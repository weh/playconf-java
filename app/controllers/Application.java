package controllers;

import controllers.routes;
import models.*;
import play.data.Form;
import play.libs.F.*;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

    private static final Form<Proposal> proposalForm = Form.form(Proposal.class);
    
    public static Promise<Result> index() {
        Promise<Proposal> keynote = Proposal.findKeynote();
        Promise<Result> result = keynote.map(new Function<Proposal, Result>() {

            @Override
            public Result apply(Proposal keynote) throws Throwable {
                return ok(views.html.index.render(keynote));
            }
        });
        return result;
    }

    public static Result newProposal() {
        return ok(views.html.newProposal.render(proposalForm));
    }

    public static Promise<Result> submit() {
        Form<Proposal> submittedForm = proposalForm.bindFromRequest();
        if (submittedForm.hasErrors()) {
            return Promise.<Result>pure(ok(views.html.newProposal.render(submittedForm)));
        } else {
            Proposal proposal = submittedForm.get();
            Promise<Result> result = proposal.asyncSave().map(new Function<Void, Result>() {
                @Override
                public Result apply(Void aVoid) throws Throwable {
                    flash("message", "Thanks for submitting a proposal");
                    return redirect(routes.Application.index());
                }
            });
            return result;
        }
    }
}
