package com.example

import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind.STRING
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import java.util.UUID

@Serializable(with = Id.Serializer::class)
class Id<@Suppress("unused") T>(val value: UUID) {
	constructor(): this(UUID.randomUUID())
	constructor(value: String): this(UUID.fromString(value))

	override fun toString() = value.toString()

	object Serializer : KSerializer<Id<*>> {
		override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("com.example.Id", STRING)

		override fun serialize(encoder: Encoder, value: Id<*>) {
			encoder.encodeString(value.toString())
		}

		override fun deserialize(decoder: Decoder): Id<*> = Id<Any>(decoder.decodeString())
	}
}

@Serializable
data class User(
	val id: Id<User>,
	val name: String,
	val address: Id<Address>,
)

data class Address(
	val id: Id<Address>,
	val street: String,
	val city: String,
	val zipCode: String,
)

fun main() {
	println(Json.encodeToString(User(Id(), "Bob", Id())))
}
