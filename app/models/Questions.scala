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

package models

import models.ccg._
import play.api.libs.json.{Format, Json}

case class OtherQuestions(
  ableToDo: Option[AbleToDo],
  howEasyScore: Option[HowEasyQuestion],
  whyGiveScore: Option[String],
  howDoYouFeelScore: Option[HowDoYouFeelQuestion]
)

object OtherQuestions {
  implicit val formats: Format[OtherQuestions] = Json.format[OtherQuestions]
}

case class PTAQuestions(
  neededToDo: Option[String],
  ableToDo: Option[AbleToDo],
  howEasyScore: Option[HowEasyQuestion],
  whyGiveScore: Option[String],
  howDoYouFeelScore: Option[HowDoYouFeelQuestion]
)

object PTAQuestions {
  implicit val formats: Format[PTAQuestions] = Json.format[PTAQuestions]
}

case class BTAQuestions(
  mainService: Option[MainServiceQuestion],
  mainServiceOther: Option[String],
  ableToDo: Option[Boolean],
  howEasyScore: Option[HowEasyQuestion],
  whyGiveScore: Option[String],
  howDoYouFeelScore: Option[HowDoYouFeelQuestion]
)

object BTAQuestions {
  implicit val formats: Format[BTAQuestions] = Json.format[BTAQuestions]
}

case class TrustsQuestions(
  isAgent: Option[Boolean],
  tryingToDo: Option[TryingToDoQuestion],
  tryingToDoOther: Option[String],
  ableToDo: Option[Boolean],
  whyNotAbleToDo: Option[String],
  howEasyScore: Option[HowEasyQuestion],
  whyGiveScore: Option[String],
  howDoYouFeelScore: Option[HowDoYouFeelQuestion]
)

object TrustsQuestions {
  implicit val formats: Format[TrustsQuestions] = Json.format[TrustsQuestions]
}

case class PensionQuestions(
  ableToDo: Option[AbleToDo],
  howEasyScore: Option[HowEasyQuestion],
  whyGiveScore: Option[String],
  howDoYouFeelScore: Option[HowDoYouFeelQuestion],
  likelyToDo: Option[LikelyToDoQuestion]
)

object PensionQuestions {
  implicit val formats: Format[PensionQuestions] = Json.format[PensionQuestions]
}

case class CCGQuestions(
  complianceCheckUnderstanding: Option[CheckUnderstandingQuestion],
  treatedProfessionally: Option[TreatedProfessionallyQuestion],
  whyGiveAnswer: Option[String],
  supportFutureTaxQuestion: Option[SupportFutureQuestion]
)

object CCGQuestions {
  implicit val formats: Format[CCGQuestions] = Json.format[CCGQuestions]
}

case class NmwCcgQuestions(
  treatedProfessionally: Option[TreatedProfessionallyQuestion],
  checkUnderstanding: Option[CheckUnderstandingQuestion],
  whyGiveAnswer: Option[String],
  supportFutureNmw: Option[SupportFutureQuestion]
)

object NmwCcgQuestions {
  implicit val formats: Format[NmwCcgQuestions] = Json.format[NmwCcgQuestions]
}

case class GiveReasonQuestions(value: Option[GiveReason], reason: Option[String])

object GiveReasonQuestions {

  implicit val formats: Format[GiveReasonQuestions] = Json.format[GiveReasonQuestions]
}
