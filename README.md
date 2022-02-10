Kotlinx Serialization Example
=============================

Example using kotlinx serialization where the compiler complains about a
generic parameters not being serializable even though it is not used in
serialization.

The `Id` class has a generic parameter which is only used for compile time
checks and is not actually used at runtime. A custom serializer for `Id<*>`,
which does not take any parameter, has been defined.

Example:

```kotlin
@Serializable
data class User(
	val id: Id<User>,
	val name: String,
	val address: Id<Address>,
)
```

In this example, the compiler does not complain about `id`, as its generic
parameter (`User`) is serializable, but it does complain about `address`:

> Serializer has not been found for type 'Address'. To use context serializer
> as fallback, explicitly annotate type or property with @Contextual

Indeed, the followin code does compile:

```kotlin
@Serializable
data class User(
	val id: Id<User>,
	val name: String,
	val address: Id<@Contextual Address>,
)
```
