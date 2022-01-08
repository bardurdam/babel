# Plurals

Babel provides first class support for plurals with the `Quantities` class.

en.conf
: @@snip [en.conf](/modules/documentation/resources/plurals/en.conf)

```scala mdoc
import cats.effect._
import cats.effect.unsafe.implicits.global
import cats.syntax.all._
import io.taig.babel._
import io.taig.babel.generic.auto._

final case class I18n(bicycles: Quantities[String])

val i18n = Loader
  .default[IO]
  .load("plurals", Set(Locales.en))
  .map(Decoder[I18n].decodeAll)
  .rethrow
  .map(_.withFallback(Locales.en))
  .flatMap(_.liftTo[IO](new IllegalStateException("Translations for en missing")))
  .map(_.apply(Locales.en))
  .unsafeRunSync()
```

```scala mdoc
i18n.bicycles(0)
```

```scala mdoc
i18n.bicycles(1)
```

```scala mdoc
i18n.bicycles(100)
```

## Using plurals with arguments

Of course, you will most likely want to reference the given quantity in your translations, which was painfully omitted in the example above. This can be achieved by combining the `StringFormat` feature with `Quantities`.

en.conf
: @@snip [en.conf](/modules/documentation/resources/plurals-arguments/en.conf)

```scala mdoc:to-string:reset
import cats.effect._
import cats.effect.unsafe.implicits.global
import cats.syntax.all._
import io.taig.babel._
import io.taig.babel.generic.auto._

final case class I18n(bicycles: Quantities[StringFormat1])

val i18n = Loader
  .default[IO]
  .load("plurals-arguments", Set(Locales.en))
  .map(Decoder[I18n].decodeAll)
  .rethrow
  .map(_.withFallback(Locales.en))
  .flatMap(_.liftTo[IO](new IllegalStateException("Translations for en missing")))
  .map(_.apply(Locales.en))
  .unsafeRunSync()
```

```scala mdoc
i18n.bicycles(0)
```

```scala mdoc
i18n.bicycles(100)("100")
```

```scala mdoc
i18n.bicycles(9000000)("9 million")
```

## Plural ranges

Some languages have very complex plural rules. These can be mapped with quantity ranges.

en.conf
: @@snip [en.conf](/modules/documentation/resources/plurals-ranges/en.conf)

```scala mdoc:to-string:reset
import cats.effect._
import cats.effect.unsafe.implicits.global
import cats.syntax.all._
import io.taig.babel._
import io.taig.babel.generic.auto._

final case class I18n(ranges: Quantities[String])

val i18n = Loader
  .default[IO]
  .load("plurals-ranges", Set(Locales.en))
  .map(Decoder[I18n].decodeAll)
  .rethrow
  .map(_.withFallback(Locales.en))
  .flatMap(_.liftTo[IO](new IllegalStateException("Translations for en missing")))
  .map(_.apply(Locales.en))
  .unsafeRunSync()
```

```scala mdoc
i18n.ranges(0)
```

```scala mdoc
i18n.ranges(1)
```

```scala mdoc
i18n.ranges(9)
i18n.ranges(10)
i18n.ranges(11)
i18n.ranges(20)
i18n.ranges(21)
```