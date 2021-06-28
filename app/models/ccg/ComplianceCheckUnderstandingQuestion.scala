/*
 * Copyright 2021 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package models.ccg

import enumeratum.EnumEntry
import models.{Enum, EnumEntryRadioItemSupport, Enumerable, RadioSupport, WithName}

import scala.collection.immutable

sealed trait ComplianceCheckUnderstandingQuestion extends EnumEntry with EnumEntryRadioItemSupport

object ComplianceCheckUnderstandingQuestion
    extends Enum[ComplianceCheckUnderstandingQuestion] with RadioSupport[ComplianceCheckUnderstandingQuestion] {

  case object VeryEasy extends WithName("VeryEasy") with ComplianceCheckUnderstandingQuestion
  case object Easy extends WithName("Easy") with ComplianceCheckUnderstandingQuestion
  case object NeitherEasyOrDifficult
      extends WithName("NeitherEasyOrDifficult") with ComplianceCheckUnderstandingQuestion
  case object Difficult extends WithName("Difficult") with ComplianceCheckUnderstandingQuestion
  case object VeryDifficult extends WithName("VeryDifficult") with ComplianceCheckUnderstandingQuestion

  override val values: immutable.IndexedSeq[ComplianceCheckUnderstandingQuestion] = findValues

  implicit val enumerable: Enumerable[ComplianceCheckUnderstandingQuestion] =
    Enumerable(values.map(v => v.toString -> v): _*)
  override val baseMessageKey: String = "complianceCheckUnderstandingQuestion"
}
