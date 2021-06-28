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

import enumeratum.EnumEntry
import play.api.libs.json._

import scala.collection.immutable

sealed trait HowEasyQuestion extends EnumEntry with EnumEntryRadioItemSupport {
  val value: Int
}

object HowEasyQuestion extends Enum[HowEasyQuestion] with RadioSupport[HowEasyQuestion] {

  case object VeryEasy extends WithName("VeryEasy") with HowEasyQuestion {
    val value = 5
  }
  case object Easy extends WithName("Easy") with HowEasyQuestion {
    val value = 4
  }
  case object Moderate extends WithName("Moderate") with HowEasyQuestion {
    val value = 3
  }
  case object Difficult extends WithName("Difficult") with HowEasyQuestion {
    val value = 2
  }
  case object VeryDifficult extends WithName("VeryDifficult") with HowEasyQuestion {
    val value = 1
  }

  override val values: immutable.IndexedSeq[HowEasyQuestion] = findValues

  implicit val enumerable: Enumerable[HowEasyQuestion] =
    Enumerable(values.map(v => v.toString -> v): _*)

  implicit object HowEasyQuestionWrites extends Writes[HowEasyQuestion] {
    def writes(howEasyQuestion: HowEasyQuestion) = Json.toJson(howEasyQuestion.toString)
  }

  implicit object HowEasyQuestionReads extends Reads[HowEasyQuestion] {
    override def reads(json: JsValue): JsResult[HowEasyQuestion] = json match {
      case JsString(VeryEasy.toString)      => JsSuccess(VeryEasy)
      case JsString(Easy.toString)          => JsSuccess(Easy)
      case JsString(Moderate.toString)      => JsSuccess(Moderate)
      case JsString(Difficult.toString)     => JsSuccess(Difficult)
      case JsString(VeryDifficult.toString) => JsSuccess(VeryDifficult)
      case _                                => JsError("Unknown howEasyQuestion")
    }
  }

  override val baseMessageKey: String = "howEasyQuestion"
}
