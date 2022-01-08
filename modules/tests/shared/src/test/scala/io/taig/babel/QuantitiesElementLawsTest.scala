package io.taig.babel

import _root_.cats.kernel.laws.discipline.EqTests
import _root_.cats.laws.discipline.TraverseTests
import io.taig.babel.cats._
import munit.DisciplineSuite
import org.scalacheck.Arbitrary

final class QuantitiesElementLawsTest extends DisciplineSuite {
  implicit def arbitrary[A: Arbitrary]: Arbitrary[Quantities.Element[A]] =
    Arbitrary(Generators.quantitiesElement(Arbitrary.arbitrary[A]))

  import Cogenerators.quantitiesElement

  checkAll("Quantities.Element", TraverseTests[Quantities.Element].traverse[Int, Double, String, Long, Option, Option])
  checkAll("Quantities.Element", EqTests[Quantities.Element[Int]].eqv)
}
