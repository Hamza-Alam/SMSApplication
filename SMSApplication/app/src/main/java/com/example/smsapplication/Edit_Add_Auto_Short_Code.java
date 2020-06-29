package com.example.smsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Edit_Add_Auto_Short_Code extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button back,autoShortBtn;
    Spinner numberSpinner;
    ArrayList<Recipients_Number_Model> arrayList;
    MyHelper myHelper;
    Recipients_Number_Adapter recipients_number_adapter;
    EditText codeName,codeString,buildCode;
    String name=null;
    String sampleString=null;
    String queryBuildString=null;
    String recipientNumber="";
    List<Character> sampleChars = new ArrayList<>();
    List<Character> buildChars = new ArrayList<>();
    List<String> charSamplePick=new ArrayList<>();
    List<String> charBuildPick=new ArrayList<>();
    boolean checkResult=true,checkVaribale=false;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    int numID;
    String selectedRadioButtonValue=null,editFlag=null,id=null,updateName=null,updateSampleString=null,updateQueryString=null,updateNumber=null,updateRadioTxt=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__add__auto__short__code);
        numberSpinner=findViewById(R.id.numberSpinner);
        numberSpinner.setOnItemSelectedListener(this);
        myHelper=new MyHelper(this);


        back=findViewById(R.id.backBtn);
        codeName=findViewById(R.id.nameEdit);
        codeString=findViewById(R.id.codeEdit);
        buildCode=findViewById(R.id.forwardEdit);
        autoShortBtn=findViewById(R.id.addShortCode);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);


        Intent intent=getIntent();
        id=intent.getStringExtra("ID");
        updateName=intent.getStringExtra("name");
        updateSampleString=intent.getStringExtra("sampleSring");
        updateQueryString=intent.getStringExtra("buildString");
        updateNumber=intent.getStringExtra("number");
        updateRadioTxt=intent.getStringExtra("functionality");
        editFlag=intent.getStringExtra("Check");


        if(id!=null && updateName!=null && updateSampleString!=null && updateQueryString!=null && updateNumber!=null && updateRadioTxt!=null && editFlag!=null){
            codeName.setText(updateName);
            codeString.setText(updateSampleString);
            buildCode.setText(updateQueryString);
        }



        autoShortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name=codeName.getText().toString();
                sampleString=codeString.getText().toString();
                queryBuildString=buildCode.getText().toString();



                /******************* Selected Radio Button Value Get ************/
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);
                selectedRadioButtonValue=radioButton.getText().toString();

                String errorMsg = "";

                if(editFlag!=null)
                {
                    if(name.length()>0 && recipientNumber.length()>0 && selectedRadioButtonValue.length()>0 && sampleString.length()>0 && queryBuildString.length()>0)
                    {
                        if (sampleString.length() == 0) {

                            Toast.makeText(getApplicationContext(), "Sample String is not allowed to leave empty", Toast.LENGTH_LONG).show();
                        }
                        else if (sampleString.startsWith("*") == false || sampleString.endsWith("#") == false) {
                            Toast.makeText(getApplicationContext(), "Sample String is not starting with * character or not ending with # character ", Toast.LENGTH_LONG).show();
//                    errorMsg = "Sample String is not starting with * character or not ending with # character ";
                        }
                        else if (sampleString.contains("{{}}")) {

                            Toast.makeText(getApplicationContext(), "Some where in Sample String, Variable name not declared between curly brackets {{, }} ", Toast.LENGTH_LONG).show();
//                    errorMsg = "Some where in Sample String, Variable name not declared between curly brackets {{, }} ";
                        }
                        else if ((sampleString.contains("{{") || sampleString.contains("}}")) && sampleString.split(Pattern.quote("{{")).length != sampleString.split(Pattern.quote("}}")).length) {
                            Toast.makeText(getApplicationContext(), "You have left variable curly brackets {{ , }}  opened or closed, Please check your Sample String ", Toast.LENGTH_LONG).show();
//                    errorMsg = "You have left variable curly brackets {{ , }}  opened or closed, Please check your Sample String ";
                        }
                        else if (queryBuildString.length() == 0) {

                            Toast.makeText(getApplicationContext(), "Query Build String is not allowed to leave empty", Toast.LENGTH_LONG).show();

                        }
                        else if (queryBuildString.startsWith("*") == false || queryBuildString.endsWith("#") == false) {

                            Toast.makeText(getApplicationContext(), "Query Build String is not starting with * character or not ending with # character ", Toast.LENGTH_LONG).show();
//                    errorMsg = "Query Build String is not starting with * character or not ending with # character ";


                        }
                        else if (queryBuildString.contains("{{}}")) {

                            Toast.makeText(getApplicationContext(), "Query Build String is not starting with * character or not ending with # character ", Toast.LENGTH_LONG).show();
//                    errorMsg = "Some where in Query Build String, Variable name not declared between curly brackets {{, }} ";


                        }
                        else if ((queryBuildString.contains("{{") || queryBuildString.contains("}}")) && queryBuildString.split(Pattern.quote("{{")).length != queryBuildString.split(Pattern.quote("}}")).length) {

                            Toast.makeText(getApplicationContext(), "You have left variable curly brackets {{ , }}  opened or closed, Please check your Query Build String ", Toast.LENGTH_LONG).show();
//                    errorMsg = "You have left variable curly brackets {{ , }}  opened or closed, Please check your Query Build String ";

                        }
                        else if (evaluateVariables(sampleString, queryBuildString).length() > 0) {

                            Toast.makeText(getApplicationContext(), "Sample String {{" + evaluateVariables(sampleString, queryBuildString) + "}} variable is missing from Query Build String", Toast.LENGTH_LONG).show();
//                    errorMsg = "Sample String {{" + evaluateVariables(sampleString, queryBuildString) + "}} variable is missing from Query Build String";

                        }
                        else if (evaluateVariables(queryBuildString, sampleString).length() > 0) {

                            Toast.makeText(getApplicationContext(), "Query Build String {{" + evaluateVariables(queryBuildString, sampleString) + "}} variable is missing from Sample String", Toast.LENGTH_LONG).show();
//                    errorMsg = "Query Build String {{" + evaluateVariables(queryBuildString,sampleString) + "}} variable is missing from Sample String";

                        }
                        else if(checkSampleStringDB(sampleString,id)){
                            Toast.makeText(getApplicationContext(), "Your Sample String Already Exist in DataBase!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            boolean resultSubmit = updateShortCode(id,name, sampleString, queryBuildString, recipientNumber);
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Please Enter All Credential CareFully !", Toast.LENGTH_LONG).show();
                    }
                }


                else {
                    if (name.length()>0 && recipientNumber.length()>0 && selectedRadioButtonValue.length()>0 && sampleString.length()>0 && queryBuildString.length()>0)
                    {
                        if (sampleString==null) {
                            Toast.makeText(getApplicationContext(), "Sample String is not allowed to leave empty", Toast.LENGTH_LONG).show();
                        }
                        else if (sampleString.startsWith("*") == false || sampleString.endsWith("#") == false) {
                            Toast.makeText(getApplicationContext(), "Sample String is not starting with * character or not ending with # character ", Toast.LENGTH_LONG).show();
//                    errorMsg = "Sample String is not starting with * character or not ending with # character ";
                        }
                        else if (sampleString.contains("{{}}")) {

                            Toast.makeText(getApplicationContext(), "Some where in Sample String, Variable name not declared between curly brackets {{, }} ", Toast.LENGTH_LONG).show();
//                    errorMsg = "Some where in Sample String, Variable name not declared between curly brackets {{, }} ";
                        }
                        else if ((sampleString.contains("{{") || sampleString.contains("}}")) && sampleString.split(Pattern.quote("{{")).length != sampleString.split(Pattern.quote("}}")).length) {
                            Toast.makeText(getApplicationContext(), "You have left variable curly brackets {{ , }}  opened or closed, Please check your Sample String ", Toast.LENGTH_LONG).show();
//                    errorMsg = "You have left variable curly brackets {{ , }}  opened or closed, Please check your Sample String ";
                        }
                        else if (queryBuildString==null) {

                            Toast.makeText(getApplicationContext(), "Query Build String is not allowed to leave empty", Toast.LENGTH_LONG).show();

                        }
                        else if (queryBuildString.startsWith("*") == false || queryBuildString.endsWith("#") == false) {

                            Toast.makeText(getApplicationContext(), "Query Build String is not starting with * character or not ending with # character ", Toast.LENGTH_LONG).show();
//                    errorMsg = "Query Build String is not starting with * character or not ending with # character ";


                        }
                        else if (queryBuildString.contains("{{}}")) {

                            Toast.makeText(getApplicationContext(), "Query Build String is not starting with * character or not ending with # character ", Toast.LENGTH_LONG).show();
//                    errorMsg = "Some where in Query Build String, Variable name not declared between curly brackets {{, }} ";


                        }
                        else if ((queryBuildString.contains("{{") || queryBuildString.contains("}}")) && queryBuildString.split(Pattern.quote("{{")).length != queryBuildString.split(Pattern.quote("}}")).length) {

                            Toast.makeText(getApplicationContext(), "You have left variable curly brackets {{ , }}  opened or closed, Please check your Query Build String ", Toast.LENGTH_LONG).show();
//                    errorMsg = "You have left variable curly brackets {{ , }}  opened or closed, Please check your Query Build String ";

                        }
                        else if (evaluateVariables(sampleString, queryBuildString).length() > 0) {

                            Toast.makeText(getApplicationContext(), "Sample String {{" + evaluateVariables(sampleString, queryBuildString) + "}} variable is missing from Query Build String", Toast.LENGTH_LONG).show();
//                    errorMsg = "Sample String {{" + evaluateVariables(sampleString, queryBuildString) + "}} variable is missing from Query Build String";

                        }
                        else if (evaluateVariables(queryBuildString, sampleString).length() > 0) {

                            Toast.makeText(getApplicationContext(), "Query Build String {{" + evaluateVariables(queryBuildString, sampleString) + "}} variable is missing from Sample String", Toast.LENGTH_LONG).show();
//                    errorMsg = "Query Build String {{" + evaluateVariables(queryBuildString,sampleString) + "}} variable is missing from Sample String";

                        }
                        else if (checkSampleStringInDB(sampleString)) {
                            Toast.makeText(getApplicationContext(), "This Sample String Starting Number Already Exists in DataBase", Toast.LENGTH_LONG).show();
                        }
                        else {
                            boolean resultSubmit = addShortCodes(name, sampleString, queryBuildString, recipientNumber);
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Please Enter All Credential CareFully !", Toast.LENGTH_LONG).show();
                    }
                }
//                if(smapleString!=null && buildString!=null && name!=null)
//                {
//                    boolean validSampleString=checkSpecialCharcter(smapleString);
//                    boolean validBuildString=checkSpecialCharcter(buildString);
//                    boolean resultSampleQuery=checkQueryString(smapleString);
//                    boolean resultBuildQuery=checkBuildQueryString(buildString);
//
//                        if(resultSampleQuery==true && resultBuildQuery==true)
//                        {
//                            if(validSampleString && validBuildString) {
//
//                                    collectAlphabets(smapleString);
//                                    collectBuildAlhapbets(buildString);
//                                if (charSamplePick.size() == charBuildPick.size()) {
//                                    int counter = 0;
//                                    for (int i = 0; i < charSamplePick.size(); i++) {
//                                        for (int j = 0; j < charBuildPick.size(); j++) {
//                                            if (charSamplePick.get(i).equals(charBuildPick.get(j))) {
//                                                counter += 1;
//                                                System.out.println(charSamplePick.get(i).toString() + " " + charSamplePick.get(i).toString());
//                                            }
//                                        }
//                                    }
//                                    if (counter == charSamplePick.size() && counter==charBuildPick.size()) {
//                                        Toast.makeText(Edit_Add_Auto_Short_Code.this, "Your String Variable Use Same !", Toast.LENGTH_LONG).show();
//                                    } else {
//                                        counter = 0;
//                                        Toast.makeText(Edit_Add_Auto_Short_Code.this, "Your String Variable Use is not Same !", Toast.LENGTH_LONG).show();
//                                    }
//                                    boolean resultSubmit = addShortCodes(name,smapleString,buildString,recipientNumber);
//                                } else {
//                                    Toast.makeText(Edit_Add_Auto_Short_Code.this, "Your Both String Are Not Same !", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                            else{
//                                Toast.makeText(Edit_Add_Auto_Short_Code.this, "Please Enter Valid String !", Toast.LENGTH_LONG).show();
//                            }
//
//                        }
//                        else {
//                            Toast.makeText(Edit_Add_Auto_Short_Code.this, "Please Check your String CareFully !", Toast.LENGTH_LONG).show();
//                        }
//                }
//                else {
//                    Toast.makeText(Edit_Add_Auto_Short_Code.this, "Please Fill Form CareFully !", Toast.LENGTH_LONG).show();
//                }
            }
        });

        getAllRecipientNumber();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Edit_Add_Auto_Short_Code.this,Auto_Short_Codes_List.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private boolean checkSampleStringDB(String sampleString, String id) {
        String [] newArray = sampleString.split(Pattern.quote("*"));
        String recordNumber = "*"+ newArray[1];
        boolean submitResult=myHelper.checkUpdateSampleStringDB(id,recordNumber);
        if(submitResult){
            return true;
        }
        else{
            return false;
        }
    }

    public String evaluateVariables(String stringInMatcher, String stringoLookIn){
        String str = "";

        String regexString = Pattern.quote("{{") + "(.*?)" + Pattern.quote("}}");
        Pattern pattern = Pattern.compile(regexString);
        Matcher matcher = pattern.matcher(stringInMatcher);

        while (matcher.find()) {
            if(stringoLookIn.contains(matcher.group(1)) == false ){
                str = matcher.group(1);
                break;
            }
        }

        return str;
    }

    public boolean checkSampleStringInDB(String sampleString){
        String [] newArray = sampleString.split(Pattern.quote("*"));
        String recordNumber = "*"+ newArray[1];
        return myHelper.checkSampleStringInDB(recordNumber);

    }
    private boolean updateShortCode(String id,String name,String query1,String query2,String number)
    {
        if(name!=null && query1!=null && query2!=null && number!=null && selectedRadioButtonValue!=null)
        {
            boolean submitResult=myHelper.updateAutoShortCode(id,name,query1,query2,number,selectedRadioButtonValue);
            if(submitResult)
            {
                Toast.makeText(Edit_Add_Auto_Short_Code.this, "Sucessfull Update Short Code!", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(Edit_Add_Auto_Short_Code.this,Auto_Short_Codes_List.class);
                startActivity(intent);
                finish();

            }
            else{
                Toast.makeText(Edit_Add_Auto_Short_Code.this, "Un-Sucessfull Update Short Code!", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(Edit_Add_Auto_Short_Code.this, "Please Enter Your Info CareFully !", Toast.LENGTH_LONG).show();
        }
        /******************* Selected Radio Button Value Get ************/
        return  true;
    }
    private boolean addShortCodes(String name,String query1,String query2,String number)
    {

        if(name!=null && query1!=null && query2!=null && number!=null && selectedRadioButtonValue!=null)
        {

            boolean submitResult=myHelper.addAutoShortCodes(name,query1,query2,number,selectedRadioButtonValue);
            if(submitResult)
            {
                Toast.makeText(Edit_Add_Auto_Short_Code.this, "Sucessfull Adding Short Code!", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(Edit_Add_Auto_Short_Code.this,Auto_Short_Codes_List.class);
                startActivity(intent);
                finish();

            }
            else{
                Toast.makeText(Edit_Add_Auto_Short_Code.this, "Un-Sucessfull Adding Short Code!", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(Edit_Add_Auto_Short_Code.this, "Please Enter Your Info CareFully !", Toast.LENGTH_LONG).show();
        }
        /******************* Selected Radio Button Value Get ************/
        return  true;
    }
//    private boolean checkSpecialCharcter(String specialString) {
//
//        boolean asterik=false;
//        boolean openBrace=false;
//        boolean closeBrace=false;
//        boolean hash=false;
//        boolean alpha=false;
//        boolean num=false;
//        boolean resultCheck=false;
//        for (int k=0;k<smapleString.length();k++) {
//            int converter = (int) smapleString.charAt(k);
//            if (converter == 42) {
//                asterik = true;
//            }else if(converter==123){
//                openBrace=true;
//            }else if(converter==125){
//                closeBrace=true;
//            }else if(converter==35){
//                hash=true;
//            }else if(converter>=48 && converter<=57){
//                num=true;
//            }else if(converter>=65 && converter<=90 || converter>=97 && converter<=122){
//                alpha=true;
//            }
//            else{
//                asterik=false;
//                openBrace=false;
//                closeBrace=false;
//                hash=false;
//                num=false;
//                alpha=false;
//                break;
//            }
//        }
//
//        if(openBrace && closeBrace && hash && asterik && num && alpha){
//            resultCheck=true;
//        }else{
//            resultCheck=false;
//        }
//        return  resultCheck;
//    }
//
//    private void collectBuildAlhapbets(String buildString) {
//        charBuildPick.clear();
//        sampleChars.clear();
//        Pattern p= Pattern.compile("[A-Za-z]+");
//        Matcher m=p.matcher(buildString);
//
//        while (m.find())
//        {
//            charBuildPick.add(m.group());
//        }
//        Log.d("Char Pick Size : ",String.valueOf(charBuildPick.size()));
//    }
//
//    private void collectAlphabets(String smapleString) {
//        charSamplePick.clear();
//        sampleChars.clear();
//        Pattern p= Pattern.compile("[A-Za-z]+");
//        Matcher m=p.matcher(smapleString);
//
//        while (m.find())
//        {
//            charSamplePick.add(m.group());
//        }
//        Log.d("Char Pick Size : ",String.valueOf(charSamplePick.size()));
//
//    }
//
//    private boolean checkBuildQueryString(String buildQuery) {
//        boolean checkBuildResult=true;
//        if(buildQuery.endsWith("#"))
//        {
//            buildChars.clear();
//            int countHash=noofHash(buildQuery);
//            if(countHash==1) {
//                for(int i=0;i<buildQuery.length();i++)
//                {
//                    buildChars.add(buildQuery.charAt(i));
//                }
//                /****************** Sample String Check Curlu Brakets ***********************/
//                for (int i=0;i<buildChars.size();i++)
//                {
//                    if(buildChars.get(i)=='{')
//                    {
//                        i=i+1;
//                        if(buildChars.get(i)!='{')
//                        {
//                            Toast.makeText(Edit_Add_Auto_Short_Code.this, "Your Start Curly Bracket Missing in Build Query!", Toast.LENGTH_LONG).show();
//                            buildCode.setText("");
//                            buildChars.clear();
//                            checkBuildResult=false;
//                            break;
//                        }
//
//                    }
//                    else if(buildChars.get(i)=='}')
//                    {
//                        i=i+1;
//                        if(buildChars.get(i)!='}')
//                        {
//                            Toast.makeText(Edit_Add_Auto_Short_Code.this, "Your End Curly Bracket Missing in Build Query!", Toast.LENGTH_LONG).show();
//                            buildCode.setText("");
//                            buildChars.clear();
//                            checkBuildResult=false;
//                            break;
//                        }
//                    }
//                }
//            }
//            else{
//                countHash=0;
//                checkBuildResult=false;
//                Toast.makeText(Edit_Add_Auto_Short_Code.this, "Your Build Query More Than 1 # ", Toast.LENGTH_LONG).show();
//            }
//        }
//        else{
//            checkBuildResult=false;
//            Toast.makeText(Edit_Add_Auto_Short_Code.this, "Your Build Query Not End With # ", Toast.LENGTH_LONG).show();
//        }
//
//        return checkBuildResult;
//    }
//
//    private boolean checkQueryString(String query) {
//        boolean checkSampleResult=true;
//        if(query.endsWith("#")) {
//
//            int countHash=noofHash(query);
//            if(countHash==1) {
//                for (int i = 0; i < query.length(); i++) {
//                    sampleChars.add(query.charAt(i));
//                }
//
//                /****************** Sample String Check Curlu Brakets ***********************/
//                for (int i = 0; i < sampleChars.size(); i++) {
//                    if (sampleChars.get(i) == '{') {
//                        i = i + 1;
//                        if (sampleChars.get(i) != '{') {
//                            Toast.makeText(Edit_Add_Auto_Short_Code.this, "Your Start Curly Bracket Missing in Sample Query!", Toast.LENGTH_LONG).show();
//                            codeString.setText("");
//                            sampleChars.clear();
//                            checkSampleResult = false;
//                            break;
//                        }
//                    } else if (sampleChars.get(i) == '}') {
//                        i = i + 1;
//                        if (sampleChars.get(i) != '}') {
//                            Toast.makeText(Edit_Add_Auto_Short_Code.this, "Your End Curly Bracket Missing in Sample Query!", Toast.LENGTH_LONG).show();
//                            codeString.setText("");
//                            sampleChars.clear();
//                            checkSampleResult = false;
//                            break;
//                        }
//                    }
//                }
//            }
//            else {
//                countHash=0;
//                checkSampleResult=false;
//                Toast.makeText(Edit_Add_Auto_Short_Code.this, "Your Sample Query More Than 1 # ", Toast.LENGTH_LONG).show();
//            }
//        }
//        else {
//            checkSampleResult=false;
//            Toast.makeText(Edit_Add_Auto_Short_Code.this, "Your Sample Query Not End With # ", Toast.LENGTH_LONG).show();
//        }
//        return checkSampleResult;
//    }
//
//    private int noofHash(String query) {
//        int countHash=0;
//        for (int i=0;i<query.length();i++)
//        {
//            if(query.charAt(i)=='#')
//            {
//                countHash=countHash+1;
//            }
//        }
//        return countHash;
//    }

    private void getAllRecipientNumber() {
        arrayList= myHelper.AllRecipientsNumber();
        recipients_number_adapter= new Recipients_Number_Adapter(Edit_Add_Auto_Short_Code.this,arrayList);
        numberSpinner.setAdapter(recipients_number_adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        numID=arrayList.get(i).id;
        System.out.println("You can Selected Id : " + String.valueOf(numID));
        recipientNumber=myHelper.getRecipientNumber(numID);
        System.out.println("Your Number is  : " + recipientNumber);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
