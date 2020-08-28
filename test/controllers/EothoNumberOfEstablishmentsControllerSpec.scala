/*
 * Copyright 2020 HM Revenue & Customs
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

package controllers

import play.api.data.Form
import play.api.libs.json.JsString
import uk.gov.hmrc.http.cache.client.CacheMap
import navigation.{FakeEothoNavigator, FakeNavigator}
import connectors.FakeDataCacheConnector
import controllers.actions._
import play.api.test.Helpers._
import forms.EothoNumberOfEstablishmentsFormProvider
import models.NormalMode
import models.EothoNumberOfEstablishments
import pages.EothoNumberOfEstablishmentsPage
import play.api.mvc.Call
import views.html.eothoNumberOfEstablishments

class EothoNumberOfEstablishmentsControllerSpec extends ControllerSpecBase {

  def onwardRoute = Call("GET", "/foo")

  val formProvider = new EothoNumberOfEstablishmentsFormProvider()
  val form = formProvider()

  def controller(dataRetrievalAction: DataRetrievalAction = getEmptyCacheMap) =
    new EothoNumberOfEstablishmentsController(
      frontendAppConfig,
      messagesApi,
      FakeDataCacheConnector,
      new FakeEothoNavigator(onwardRoute),
      FakeIdentifierAction,
      dataRetrievalAction,
      new DataRequiredActionImpl,
      formProvider,
      mcc
    )

  def viewAsString(form: Form[_] = form) =
    eothoNumberOfEstablishments(frontendAppConfig, form, NormalMode)(fakeRequest, messages).toString

  "EothoNumberOfEstablishments Controller" must {

    "return OK and the correct view for a GET" in {
      val result = controller().onPageLoad(NormalMode)(fakeRequest)

      status(result) mustBe OK
      contentAsString(result) mustBe viewAsString()
    }

    "populate the view correctly on a GET when the question has previously been answered" in {
      val validData =
        Map(EothoNumberOfEstablishmentsPage.toString -> JsString(EothoNumberOfEstablishments.values.head.toString))
      val getRelevantData = new FakeDataRetrievalAction(Some(CacheMap(cacheMapId, validData)))

      val result = controller(getRelevantData).onPageLoad(NormalMode)(fakeRequest)

      contentAsString(result) mustBe viewAsString(form.fill(EothoNumberOfEstablishments.values.head))
    }

    "redirect to the next page when valid data is submitted" in {
      val postRequest = fakeRequest.withFormUrlEncodedBody(("value", EothoNumberOfEstablishments.options.head.value))

      val result = controller().onSubmit(NormalMode)(postRequest)

      status(result) mustBe SEE_OTHER
      redirectLocation(result) mustBe Some(onwardRoute.url)
    }

    "return a Bad Request and errors when invalid data is submitted" in {
      val postRequest = fakeRequest.withFormUrlEncodedBody(("value", "invalid value"))
      val boundForm = form.bind(Map("value" -> "invalid value"))

      val result = controller().onSubmit(NormalMode)(postRequest)

      status(result) mustBe BAD_REQUEST
      contentAsString(result) mustBe viewAsString(boundForm)
    }

    "return OK and the correct view for a GET with no existing data" in {
      val result = controller(dontGetAnyData).onPageLoad(NormalMode)(fakeRequest)

      status(result) mustBe OK
      contentAsString(result) mustBe viewAsString()
    }

    "redirect to the next page when valid data is submitted with no existing data" in {
      val postRequest = fakeRequest.withFormUrlEncodedBody(("value", EothoNumberOfEstablishments.options.head.value))
      val result = controller(dontGetAnyData).onSubmit(NormalMode)(postRequest)

      status(result) mustBe SEE_OTHER
      redirectLocation(result) mustBe Some(onwardRoute.url)
    }
  }
}