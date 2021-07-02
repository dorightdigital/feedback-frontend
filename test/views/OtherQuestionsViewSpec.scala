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

package views

import play.api.data.Form
import forms.OtherQuestionsFormProvider
import views.behaviours.{OptionsViewBehaviours, StringViewBehaviours, YesNoViewBehaviours}
import models.{AbleToDo, HowDoYouFeelQuestion, HowEasyQuestion, Origin, OtherQuestions}
import views.html.{OtherQuestions => OtherQuestionsView}

class OtherQuestionsViewSpec
    extends YesNoViewBehaviours[OtherQuestions] with StringViewBehaviours[OtherQuestions]
    with OptionsViewBehaviours[OtherQuestions] {

  val messageKeyPrefix = "otherQuestions"

  val form = new OtherQuestionsFormProvider()()
  val action = controllers.routes.OtherQuestionsController.onPageLoad(Origin.fromString("origin"))

  lazy val otherQuestions = inject[OtherQuestionsView]

  def createView = () => otherQuestions(frontendAppConfig, form, action)(fakeRequest, messages)

  def createViewUsingForm =
    (form: Form[_]) => otherQuestions(frontendAppConfig, form, action)(fakeRequest, messages)

  "OtherQuestions view" must {

    behave like normalPageNew(createView, messageKeyPrefix, "intro1", "intro3")

    behave like optionsPageWithRadioItems(
      createViewUsingForm,
      "ableToDo",
      AbleToDo.options(form),
      "otherQuestions.ableToDo")

    behave like optionsPageWithRadioItems(
      createViewUsingForm,
      "howEasyScore",
      HowEasyQuestion.options(form),
      "otherQuestions.howEasyScore")

    behave like stringPageNew(createViewUsingForm, "whyGiveScore", "otherQuestions.whyGiveScore")

    behave like optionsPageWithRadioItems(
      createViewUsingForm,
      "howDoYouFeelScore",
      HowDoYouFeelQuestion.options(form),
      "otherQuestions.howDoYouFeelScore")

    "contain second introductory paragraph" in {
      val expectedMessage = messages("otherQuestions.intro2", messages("otherQuestions.introLinkText"))
      val doc = asDocument(createView())
      assertContainsText(doc, expectedMessage)
    }

    "contain privacy anchor tag" in {
      val expectedLink = messages("otherQuestions.introLinkText")
      val doc = asDocument(createView())
      assertContainsLink(doc, expectedLink, frontendAppConfig.privacyPolicyUrl)
    }
  }
}
