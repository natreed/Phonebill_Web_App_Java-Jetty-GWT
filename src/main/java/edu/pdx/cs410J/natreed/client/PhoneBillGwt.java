package edu.pdx.cs410J.natreed.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {
    private final Alerter alerter;
    private final PhoneBillServiceAsync phoneBillService;
    private final Logger logger;
    private final VerticalPanel vp = new VerticalPanel();


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

    private void setupUI() {
        RootPanel.get().add(vp);

        //Create header
        Label optionLabel = new Label("Choose an Option");
        ListBox optionBox = new ListBox();
        optionBox.addItem("Print Phonebill");
        optionBox.addItem("Add Phonecall");
        optionBox.addItem("Search Phonecalls");
        optionBox.addItem("README");
        Button submitButton = new Button("Submit");
        Button helpButton = new Button("Help");

        Grid header = new Grid(1, 4);
        header.setWidget(0, 0, optionLabel);
        header.setWidget(0, 1, optionBox);
        header.setWidget(0, 2, submitButton);
        header.setWidget(0, 3, helpButton);


        Label paddingLabel = new Label("");

        //Create List of Widgets
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


        //Get Add Phonecall Grid
        Grid userInputGrid = new Grid(1, 2);
        userInputGrid.setWidget(0, 0, nameLabel);
        userInputGrid.setWidget(0, 1, nameBox);


        //Text Area
        TextArea ta = new TextArea();
        ta.setCharacterWidth(150);
        ta.setVisibleLines(30);

        Grid textAreaGrid = new Grid(1, 1);
        textAreaGrid.setWidget(0, 0, ta);

        //Get everything lined up
        header.setCellPadding(10);
        userInputGrid.setCellPadding(10);
        textAreaGrid.setCellPadding(10);

        vp.add(header);
        vp.add(userInputGrid);
        vp.add(textAreaGrid);

        //for testing
        nameBox.setText("bill");
        calleeBox.setText("333-333-3333");
        callerBox.setText("444-444-4444");
        startTimeBox.setText("12/03/2013 8:00 am");
        endTimeBox.setText(("12/03/2013 8:01 am"));

        //indexes for select options
        //    0("Print Phonebill");
        //    1("Add Phonecall");
        //    2("Search Phonecalls");
        //    3("Help");
        //    4("Readme");
        optionBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                int op = optionBox.getSelectedIndex();
                if (op == 0) {
                    userInputGrid.resize(1, 2);
                    userInputGrid.setWidget(0, 0, nameLabel);
                    userInputGrid.setWidget(0, 1, nameBox);
                } else if (op == 1) {
                    userInputGrid.resize(5, 2);
                    userInputGrid.setWidget(0, 0, nameLabel);
                    userInputGrid.setWidget(0, 1, nameBox);
                    userInputGrid.setWidget(1, 0, callerLabel);
                    userInputGrid.setWidget(1, 1, callerBox);
                    userInputGrid.setWidget(2, 0, calleeLabel);
                    userInputGrid.setWidget(2, 1, calleeBox);
                    userInputGrid.setWidget(3, 0, startTimeLabel);
                    userInputGrid.setWidget(3, 1, startTimeBox);
                    userInputGrid.setWidget(4, 0, endTimeLabel);
                    userInputGrid.setWidget(4, 1, endTimeBox);
                } else if (op == 2) {

                }
            }
        });

        submitButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                int op = optionBox.getSelectedIndex();
                String name = nameBox.getText();
                String caller = callerBox.getText();
                String callee = calleeBox.getText();
                String startTime = startTimeBox.getText();
                String endTime  = endTimeBox.getText();

                submitHandler (op, name, caller, callee, startTime, endTime, ta, userInputGrid);
            }
        });

        helpButton.addClickHandler((new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                ta.setText(README.printReadMe());
            }
        }));
    }

    private void submitHandler(int op, String name, String caller,
                               String callee, String startTime, String endTime, TextArea ta, Grid grid) {
        //collapse grid
        if (op == 0) {
            getPrettyPhoneBill(name, ta);
        } else if (op == 1) {
            Parser.callerCalleeCheck(caller, callee);
            PhoneCall phoneCall = new PhoneCall(caller, callee,
                    DateAndTime.StringToDate(startTime),
                    DateAndTime.StringToDate(endTime));
            addPhoneCall(name, phoneCall, ta);

        } else if (op == 2) {
            Date start = DateAndTime.StringToDate(startTime);
            Date end = DateAndTime.StringToDate(endTime);
            searchCalls(name, start, end, ta);
        } else if (op ==3) {
            ta.setText(README.printReadMe());
        }
    }

    public void addPhoneCall(String customerName, PhoneCall call, TextArea ta) {
        phoneBillService.addPhoneCall(customerName, call, new AsyncCallback() {
            @Override
            public void onFailure(Throwable ex) {
                alerter.alert(ex.getMessage());
            }

            @Override
            public void onSuccess(Object o) {
                StringBuilder sb = new StringBuilder("Phonecall added for " + customerName + "\n" + call.toString());
                ta.setText(sb.toString());
            }
        });
    }

    private void searchCalls(String customer, Date begin, Date end, TextArea ta) {
        phoneBillService.getPhoneBillFor(customer, new AsyncCallback<PhoneBill>() {
            @Override
            public void onFailure(Throwable ex) {
                alerter.alert(ex.getMessage());
            }

            @Override
            public void onSuccess(PhoneBill phoneBill) {
                StringBuilder sb = new StringBuilder(phoneBill.callsByTimeRange(DateAndTime.dateToString(begin),
                        DateAndTime.dateToString(end)));
                ta.setText(sb.toString());
            }
        });
    }


    private void getPrettyPhoneBill(String customer, TextArea ta) {
        logger.info("Calling getPhoneBill");
        phoneBillService.printBill(customer, new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable ex) {
                alerter.alert("No call records exist for " + customer + ". If you would like" +
                        " to create a new phone bill choose add call and enter the call information.");
            }

            @Override
            public void onSuccess(String s) {
                ta.setText(s);
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
