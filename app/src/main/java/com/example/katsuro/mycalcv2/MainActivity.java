package com.example.katsuro.mycalcv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MyCalculator";
    public final static String EXTRA_MESSAGE = "com.example.katsuro.mycalv2.MESSAGE";
    private final static Character CHAR_MINUS = '-';
    private final static Character CHAR_PLUS = '+';
    private final static Character CHAR_MULTIPLY = '×';
    private final static Character CHAR_DIVISION = '÷';
    private final static String EQUALS = "=";
    private final static String EMPTY_STRING = "";
    private final static String NEW_LINE ="\n";
    private final static int LEN_EQUALS_ZERO = 0;
    private final static int LEN_EQUALS_ONE = 1;
    private final static int LEN_EQUALS_TWO = 2;
    private Intent intent;
    private Button actuallyButton;
    private TextView textViewResults;
    private List<String> listOfResults;
    private String text;
    private String currentOperation;
    private String firstChar;
    private String message;
    private String[] operation;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResults = (TextView) findViewById(R.id.textViewResults);
        listOfResults = new ArrayList<String>();
        text = EMPTY_STRING;
        currentOperation = EMPTY_STRING;
        firstChar = EMPTY_STRING;
        sendMessage();
    }

    public void sendMessage()
    {
        if(listOfResults.isEmpty())
        {
            textViewResults.setText(text);
        }
        else if(listOfResults.size() == LEN_EQUALS_ONE)
        {
            textViewResults.setText(listOfResults.get(LEN_EQUALS_ZERO) + NEW_LINE + text);
        }
        else if(listOfResults.size() > LEN_EQUALS_ONE)
        {
            textViewResults.setText(listOfResults.get(listOfResults.size()-LEN_EQUALS_TWO) + NEW_LINE + listOfResults.get(listOfResults.size()-LEN_EQUALS_ONE) + NEW_LINE + text);
        }
    }

    public void buttonNumberClick(View view)
    {
        actuallyButton = (Button)view;
        text += actuallyButton.getText();
        sendMessage();
    }

    public boolean isOperator(char operation)
    {
        switch(operation)
        {
            case '+':
                return true;
            case '-':
                return true;
            case '×':
                return true;
            case '÷':
                return true;
            default:
                return false;
        }
    }

    public void buttonOperationClick(View view)
    {
        actuallyButton = (Button)view;
        if(text.isEmpty() && actuallyButton.getId() == R.id.buttonMinus)
        {
            firstChar = actuallyButton.getText().toString();
            Log.i(TAG, "First char: " + firstChar);
            text +=actuallyButton.getText();
            sendMessage();
        }
        else if(text.length() == LEN_EQUALS_ONE && text.charAt(LEN_EQUALS_ZERO) == CHAR_MINUS)
        {
            Log.i(TAG,"Do nothing");
            //do nothing
        }
        else if(!text.isEmpty())
        {
            if(currentOperation != EMPTY_STRING && (text.charAt(text.length()-LEN_EQUALS_ONE) == CHAR_MINUS || text.charAt(text.length()-LEN_EQUALS_ONE) == CHAR_PLUS || text.charAt(text.length()-LEN_EQUALS_ONE) == CHAR_MULTIPLY || text.charAt(text.length()-LEN_EQUALS_ONE) == CHAR_DIVISION))
            {
                if (isOperator(text.charAt(text.length() - LEN_EQUALS_ONE)))
                {
                    Log.i(TAG,"Last operation: " + currentOperation);
                    currentOperation = actuallyButton.getText().toString();
                    Log.i(TAG, "New operation:" + currentOperation);
                    text = text.substring(LEN_EQUALS_ZERO, text.length() - LEN_EQUALS_ONE);
                    text += actuallyButton.getText();
                    sendMessage();
                }
                else
                {
                    currentOperation = actuallyButton.getText().toString();
                    Log.i(TAG,"Current operation: " + currentOperation);
                    text += actuallyButton.getText();
                    sendMessage();
                }
            }
            else if(currentOperation == EMPTY_STRING)
            {
                currentOperation = actuallyButton.getText().toString();
                Log.i(TAG,"Current operation: " + currentOperation);
                text += actuallyButton.getText();
                sendMessage();
            }
        }
    }

    public void getResult(String expression)
    {
        operation = expression.split(Pattern.quote(currentOperation));
        if (operation.length < LEN_EQUALS_TWO || currentOperation == EMPTY_STRING)
        {
            Log.e(TAG,"To short operation.");
        }
        else
        {
            if (firstChar == EMPTY_STRING)
            {
                text += "=" + String.valueOf(operate(operation[LEN_EQUALS_ZERO], operation[LEN_EQUALS_ONE], currentOperation));
                Log.i(TAG,"Operation: " + operation[LEN_EQUALS_ZERO] + currentOperation + operation[LEN_EQUALS_ONE] + EQUALS + String.valueOf(operate(operation[LEN_EQUALS_ZERO], operation[LEN_EQUALS_ONE], currentOperation)));
            }
            else
            {
                text += "=" + String.valueOf(operate(CHAR_MINUS+operation[LEN_EQUALS_ZERO], operation[LEN_EQUALS_ONE], currentOperation));
                Log.i(TAG,"Operation: " + CHAR_MINUS + operation[LEN_EQUALS_ZERO] + currentOperation + operation[LEN_EQUALS_ONE] + EQUALS + String.valueOf(operate(operation[LEN_EQUALS_ZERO], operation[LEN_EQUALS_ONE], currentOperation)));
            }
            listOfResults.add(text);
            clear();
            sendMessage();
        }
    }

    public void buttonEqualsClick(View view)
    {
        if(firstChar == EMPTY_STRING)
        {
            getResult(text);
        }
        else
        {
            getResult(text.substring(LEN_EQUALS_ONE,text.length()));
        }
    }

    private double operate(String firstNumber, String secondNumber, String operation)
    {
        switch(operation)
        {
            case "+":
                return Double.valueOf(firstNumber) + Double.valueOf(secondNumber);
            case "-":
                return Double.valueOf(firstNumber) - Double.valueOf(secondNumber);
            case "×":
                return Double.valueOf(firstNumber) * Double.valueOf(secondNumber);
            case "÷":
                try
                {
                    return Double.valueOf(firstNumber) / Double.valueOf(secondNumber);
                }
                catch (Exception e)
                {
                    Log.d(TAG, e.getMessage());
                }
            default:
                return -1;
        }
    }

    private void clear()
    {
        currentOperation = EMPTY_STRING;
        text = EMPTY_STRING;
        firstChar = EMPTY_STRING;
    }

    public void buttonArrowClick(View view)
    {
        if(text.length() > LEN_EQUALS_ZERO)
        {
            if(text.length() == LEN_EQUALS_ONE )
            {
                firstChar = EMPTY_STRING;
                Log.i(TAG,"First char is empty.");
                text = text.substring(LEN_EQUALS_ZERO, text.length()-LEN_EQUALS_ONE);
            }
            else if(text.length() >= LEN_EQUALS_TWO && (text.charAt(text.length()-LEN_EQUALS_ONE) == CHAR_PLUS || text.charAt(text.length()-LEN_EQUALS_ONE) == CHAR_MINUS || text.charAt(text.length()-LEN_EQUALS_ONE) == CHAR_MULTIPLY || text.charAt(text.length()-LEN_EQUALS_ONE) == CHAR_DIVISION))
            {
                currentOperation = EMPTY_STRING;
                Log.i(TAG,"Current operation is empty.");
                text = text.substring(LEN_EQUALS_ZERO, text.length()-LEN_EQUALS_ONE);
            }
            else
            {
                text = text.substring(LEN_EQUALS_ZERO, text.length()-LEN_EQUALS_ONE);
            }
            sendMessage();
        }
    }

    public void buttonClearClick(View view)
    {
        clear();
        sendMessage();
    }

    public void buttonListClick(View view)
    {
        Log.i(TAG,"Opening history.");
        message = EMPTY_STRING;
        intent = new Intent(this, ListActivity.class);
        for(int i=0; i < listOfResults.size(); ++i)
        {
            message += listOfResults.get(i) + NEW_LINE;
        }
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void buttonClearListClick(View view)
    {
        if(listOfResults.size() > LEN_EQUALS_ZERO)
        {
            Log.i(TAG,"Deleting history.");
            listOfResults.clear();
            sendMessage();
        }
    }
}
