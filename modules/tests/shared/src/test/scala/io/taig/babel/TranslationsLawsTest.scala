package io.taig.babel

import _root_.cats.kernel.laws.discipline.EqTests
import _root_.cats.laws.discipline.{MonoidKTests, TraverseTests}
import io.taig.babel.cats._
import munit.DisciplineSuite
import org.scalacheck.Arbitrary

final class TranslationsLawsTest extends DisciplineSuite {
  implicit def arbitrary[A: Arbitrary]: Arbitrary[Translations[A]] =
    Arbitrary(Generators.translations(Arbitrary.arbitrary[A]))

  import Cogenerators.translations

  checkAll("Translations", TraverseTests[Translations].traverse[Int, Double, String, Long, Option, Option])
  checkAll("Translations", MonoidKTests[Translations].monoidK[Int])
  checkAll("Translations", EqTests[Translations[Int]].eqv)
}
