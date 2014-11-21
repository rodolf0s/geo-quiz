package com.knnwulf.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends Activity {
	
	private static final String TAG = "QuizActivity";
	
	private static final String KEY_INDEX = "index";
	
	private Button mTrueButton;
	private Button mFalseButton;
	private Button mNextButton;
	private Button mPrevButton;
	private Button mCheatButton;
	private	TextView mQuestionTextView;
	
	private TrueFalse[] mQuestionBank = new TrueFalse[] {
			new TrueFalse(R.string.question_ocean, true),
			new TrueFalse(R.string.question_mideast, false),
			new TrueFalse(R.string.question_africa, false),
			new TrueFalse(R.string.question_america, true),
			new TrueFalse(R.string.question_asia, true),
	};
	
	private int mCurrentIndex = 0;
	private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(bundle) called");
        setContentView(R.layout.activity_quiz);
        
        if (savedInstanceState != null) {
        	mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
        }
        
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
        
        mTrueButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});
        
        mFalseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});
        
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				mIsCheater = false;
				updateQuestion();
			}
		});
        
        mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
				mCurrentIndex = mCurrentIndex < 0 ? mQuestionBank.length - 1 : mCurrentIndex;
				mIsCheater = false;
				updateQuestion();
			}
		});
        
        mCheatButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(QuizActivity.this, CheatActivity.class);
				boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
				i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
				startActivityForResult(i, 0);
			}
		});
        
        updateQuestion();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	//super.onActivityResult(requestCode, resultCode, data);
    	if (data == null) {
    		return;
    	}
    	mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	Log.i(TAG, "onSaveInstanceState()");
    	outState.putInt(KEY_INDEX, mCurrentIndex);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void updateQuestion() {
    	int question = mQuestionBank[mCurrentIndex].getQuestion();
    	mQuestionTextView.setText(question);
    }
    
    private void checkAnswer(boolean userPressedTrue) {
    	boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
    	
    	int messageResId = 0;
    	if (mIsCheater) {
	    	messageResId = R.string.judgment_toast;
    	} else {
    		if (userPressedTrue == answerIsTrue) {
	    		messageResId = R.string.correct_toast;
	    	} else {
	    		messageResId = R.string.incorrect_toast;
	    	}
    	}
    	
    	Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	Log.d(TAG, "onStart() called");
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	Log.d(TAG, "onPause() called");
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	Log.d(TAG, "onResume() called");
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	Log.d(TAG, "onStop() called");
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	Log.d(TAG, "onDestroy() called");
    }
}
