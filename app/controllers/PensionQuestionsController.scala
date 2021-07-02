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

package controllers

import config.FrontendAppConfig
import forms.PensionQuestionsFormProvider
import javax.inject.Inject
import models.{FeedbackId, Origin, PensionQuestions}
import navigation.Navigator
import pages.PensionQuestionsPage
import play.api.data.Form
import play.api.i18n.I18nSupport
import play.api.mvc.MessagesControllerComponents
import services.AuditService
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import views.html.{PensionQuestions => PensionQuestionsView}

class PensionQuestionsController @Inject()(
  appConfig: FrontendAppConfig,
  navigator: Navigator,
  formProvider: PensionQuestionsFormProvider,
  auditService: AuditService,
  mcc: MessagesControllerComponents,
  pensionQuestions: PensionQuestionsView)
    extends FrontendController(mcc) with I18nSupport {

  val form: Form[PensionQuestions] = formProvider()
  lazy val successPage = navigator.nextPage(PensionQuestionsPage)(())
  def submitCall(origin: Origin) = routes.PensionQuestionsController.onSubmit(origin)

  def onPageLoad(origin: Origin) = Action { implicit request =>
    Ok(pensionQuestions(appConfig, form, submitCall(origin)))
  }

  def onSubmit(origin: Origin) = Action { implicit request =>
    form
      .bindFromRequest()
      .fold(
        formWithErrors => BadRequest(pensionQuestions(appConfig, formWithErrors, submitCall(origin))),
        value => {
          auditService.pensionAudit(origin, FeedbackId.fromSession, value)
          Redirect(successPage)
        }
      )
  }
}
