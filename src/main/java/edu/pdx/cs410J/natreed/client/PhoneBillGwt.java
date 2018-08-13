package edu.pdx.cs410J.natreed.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {
  private final Alerter alerter;
  private final PhoneBillServiceAsync phoneBillService;
  private final Logger logger;


  @VisibleForTesting
  Button showPhoneBillButton;

  @VisibleForTesting
  Button showUndeclaredExceptionButton;

  @VisibleForTesting
  Button showDeclaredExceptionButton;

  @VisibleForTesting
  Button showClientSideExceptionButton;

  public PhoneBillGwt() {
    this(new Alerter() {
      @Override
      public void alert(String message) {
        Window.alert(message);
      }
    });
  }

  @VisibleForTesting
  PhoneBillGwt(Alerter alerter) {
    this.alerter = alerter;
    this.phoneBillService = GWT.create(PhoneBillService.class);
    this.logger = Logger.getLogger("phoneBill");
    Logger.getLogger("").setLevel(Level.INFO);  // Quiet down the default logging
  }

  private void alertOnException(Throwable throwable) {
    Throwable unwrapped = unwrapUmbrellaException(throwable);
    StringBuilder sb = new StringBuilder();
    sb.append(unwrapped.toString());
    sb.append('\n');

    for (StackTraceElement element : unwrapped.getStackTrace()) {
      sb.append("  at ");
      sb.append(element.toString());
      sb.append('\n');
    }

    this.alerter.alert(sb.toString());
  }

  private Throwable unwrapUmbrellaException(Throwable throwable) {
    if (throwable instanceof UmbrellaException) {
      UmbrellaException umbrella = (UmbrellaException) throwable;
      if (umbrella.getCauses().size() == 1) {
        return unwrapUmbrellaException(umbrella.getCauses().iterator().next());
      }

    }

    return throwable;
  }


  private void addWidgets(FormPanel form) {
    // Create a FormPanel and point it at a service.

    form.setAction("/myFormHandler");

    // Because we're going to add a FileUpload widget, we'll need to set the
    // form to use the POST method, and multipart MIME encoding.
    form.setEncoding(FormPanel.ENCODING_MULTIPART);
    form.setMethod(FormPanel.METHOD_POST);

    // Create a panel to hold all of the form widgets.
    VerticalPanel panel = new VerticalPanel();
    form.setWidget(panel);

    // Create a TextBox, giving it a name so that it will be submitted.
    final TextBox tb = new TextBox();
    tb.setName("textBoxFormElement");
    panel.add(tb);

    // Create a ListBox, giving it a name and some values to be associated with
    // its options.
    ListBox lb = new ListBox();
    lb.setName("listBoxFormElement");
    lb.addItem("foo", "fooValue");
    lb.addItem("bar", "barValue");
    lb.addItem("baz", "bazValue");
    panel.add(lb);

    // Create a FileUpload widget.
    FileUpload upload = new FileUpload();
    upload.setName("uploadFormElement");
    panel.add(upload);

    // Add a 'submit' button.
    panel.add(new Button("Submit", new ClickHandler() {
      public void onClick(ClickEvent event) {
        form.submit();
      }
    }));

    // Add an event handler to the form.
    form.addSubmitHandler(new FormPanel.SubmitHandler() {
      public void onSubmit(FormPanel.SubmitEvent event) {
        // This event is fired just before the form is submitted. We can take
        // this opportunity to perform validation.
        if (tb.getText().length() == 0) {
          Window.alert("The text box must not be empty");
          event.cancel();
        }
      }
    });
    form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
      public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
        // When the form submission is successfully completed, this event is
        // fired. Assuming the service returned a response of type text/html,
        // we can get the result text here (see the FormPanel documentation for
        // further explanation).
        Window.alert(event.getResults());
      }
    });

    RootPanel.get().add(form);
  }




  private void throwClientSideException() {
    logger.info("About to throw a client-side exception");
    throw new IllegalStateException("Expected exception on the client side");
  }

  private void showUndeclaredException() {
    logger.info("Calling throwUndeclaredException");
    phoneBillService.throwUndeclaredException(new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(Void aVoid) {
        alerter.alert("This shouldn't happen");
      }
    });
  }

  private void showDeclaredException() {
    logger.info("Calling throwDeclaredException");
    phoneBillService.throwDeclaredException(new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(Void aVoid) {
        alerter.alert("This shouldn't happen");
      }
    });
  }

  private void showPhoneBill() {
    logger.info("Calling getPhoneBill");
    phoneBillService.getPhoneBill(new AsyncCallback<PhoneBill>() {

      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(PhoneBill phoneBill) {
        StringBuilder sb = new StringBuilder(phoneBill.toString());
        Collection<PhoneCall> calls = phoneBill.getPhoneCalls();
        for (PhoneCall call : calls) {
          sb.append(call);
          sb.append("\n");
        }
        alerter.alert(sb.toString());
      }
    });
  }
  
  @Override
  public void onModuleLoad() {
    setUpUncaughtExceptionHandler();

    // The UncaughtExceptionHandler won't catch exceptions during module load
    // So, you have to set up the UI after module load...
    Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
      @Override
      public void execute() {
        setupUI();
      }
    });
  }

  //
  private void setupUI() {
    RootPanel rootPanel = RootPanel.get();
////    final FormPanel form = new FormPanel();
////    rootPanel.add(form);
////
////    addWidgets(form);
    Label optionLabel = new Label("Choose an Option");
    ListBox optionBox = new ListBox();
    optionBox.addItem("Print Phonebill");
    optionBox.addItem("Create Phonebill");
    optionBox.addItem("Add Phonecall");
    optionBox.addItem("Search Phonecalls");
    optionBox.addItem("Help");
    optionBox.addItem("Readme");

    Label nameLabel = new Label("Name:");
    TextBox nameBox = new TextBox();
    Label callerLabel = new Label("Caller:");
    TextBox callerBox = new TextBox();
    Label calleeLabel = new Label("Callee:");
    TextBox calleeBox = new TextBox();
    Label startTimeLabel = new Label("Start Time:");
    TextBox startTimeBox = new TextBox();
    Label endTimeLabel = new Label("End Time:");
    TextBox endTimeBox = new TextBox();
    Button button = new Button("Submit");

    Grid grid = new Grid(7, 2);

    grid.setWidget(0, 0, optionLabel);
    grid.setWidget(0, 1, optionBox);
    grid.setWidget(1, 0, nameLabel);
    grid.setWidget(1, 1, nameBox);
    grid.setWidget(2, 0, callerLabel);
    grid.setWidget(2, 1, callerBox);
    grid.setWidget(3, 0, calleeLabel);
    grid.setWidget(3, 1, calleeBox);
    grid.setWidget(4, 0, startTimeLabel);
    grid.setWidget(4, 1, startTimeBox);
    grid.setWidget(5, 0, endTimeLabel);
    grid.setWidget(5, 1, endTimeBox);
    grid.setWidget(6, 1, button);


    RootPanel.get().add(grid);

    optionBox.addClickListener(new ClickListener() {
      @Override
      public void onClick(Widget widget) {
        String text = optionBox.getSelectedItemText();
        if (text == "Print Phonebill") {

        }
        else if (text == "Add Phonecall") {

        }
        else if (text == "Search Phonecalls") {

        }
        else if (text == "Help") {

        }
        else if (text == "Readme") {

        }
        else if (text == "Create Phonebill") {

        }
      }
    });

  }

  private void setUpUncaughtExceptionHandler() {
    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(Throwable throwable) {
        alertOnException(throwable);
      }
    });
  }

  @VisibleForTesting
  interface Alerter {
    void alert(String message);
  }

}
