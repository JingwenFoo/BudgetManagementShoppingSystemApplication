package com.example.budgetmanagementshoppingsystemapplication.ManageBudgetTracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetmanagementshoppingsystemapplication.ManageAccount.CustomerHomepage;
import com.example.budgetmanagementshoppingsystemapplication.ManageAccount.MainActivity;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.example.budgetmanagementshoppingsystemapplication.Model.preferences;

import java.util.Objects;

public class Budget extends AppCompatActivity {

    Button GoShopBtn;
    EditText ed_bud;
    SeekBar sb_fresh, sb_groc, sb_bev, sb_house, sb_cloth, sb_PCare;
    TextView tv_fresh, tv_groc, tv_bev, tv_house, tv_cloth, tv_PCare, freshBud, grocBud, bevBud, houseBud, PCBud, clothBud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        GoShopBtn = findViewById(R.id.button4);
        ed_bud = findViewById(R.id.editTextBudget);
        sb_fresh = findViewById(R.id.seekBarFresh);
        sb_groc = findViewById(R.id.seekBarGroc);
        sb_bev = findViewById(R.id.seekBarBeverages);
        sb_house = findViewById(R.id.seekBarHouse);
        sb_cloth = findViewById(R.id.seekBarCloth);
        sb_PCare = findViewById(R.id.seekBarPC);
        tv_fresh = findViewById(R.id.freshPer);
        tv_groc = findViewById(R.id.grocPer);
        tv_bev = findViewById(R.id.bevPer);
        tv_house = findViewById(R.id.housPer);
        tv_cloth = findViewById(R.id.clothPer);
        tv_PCare = findViewById(R.id.PCPer);
        freshBud = findViewById(R.id.freshBud);
        grocBud = findViewById(R.id.grocBud);
        bevBud = findViewById(R.id.bevBud);
        houseBud = findViewById(R.id.houseBud);
        PCBud = findViewById(R.id.PCBud);
        clothBud = findViewById(R.id.clothBud);

        ed_bud.setText("");
        tv_fresh.setText("0");
        tv_groc.setText("0");
        tv_house.setText("0");
        tv_bev.setText("0");
        tv_PCare.setText("0");
        tv_cloth.setText("0");

        ed_bud.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                {
                    InputMethodManager inputMethodManager = (InputMethodManager) Objects.requireNonNull(Objects.requireNonNull(Budget.this)).getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
                }
            }
        });

        sb_fresh.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue;
            int totalPercentage;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressValue = progress;
                    tv_fresh.setText(String.valueOf(progress));
                    totalPercentage = sb_fresh.getProgress()+sb_groc.getProgress()+sb_bev.getProgress()+sb_house.getProgress()+sb_PCare.getProgress()+sb_cloth.getProgress();

                    if (progressValue<100)
                    {
                        if (totalPercentage>=100)
                        {
                            int max = 100 - (sb_PCare.getProgress()+sb_groc.getProgress()+sb_bev.getProgress()+sb_house.getProgress()+sb_cloth.getProgress());
                            sb_fresh.setProgress(max);
                        }

                        sb_groc.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_groc.setMax(100);

                        sb_house.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_house.setMax(100);

                        sb_bev.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_bev.setMax(100);

                        sb_PCare.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_PCare.setMax(100);

                        sb_cloth.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_cloth.setMax(100);
                    }
                    else  {
                        sb_groc.setMax(0);
                        sb_groc.clearAnimation();
                        sb_groc.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_house.setMax(0);
                        sb_house.clearAnimation();
                        sb_house.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_bev.setMax(0);
                        sb_bev.clearAnimation();
                        sb_bev.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_PCare.setMax(0);
                        sb_PCare.clearAnimation();
                        sb_PCare.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_cloth.setMax(0);
                        sb_cloth.clearAnimation();
                        sb_cloth.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    tv_fresh.setText(String.valueOf(progressValue));
                    freshBud.setText("RM "+(String.format("%.2f",(progressValue/100)*Float.parseFloat(ed_bud.getText().toString()))));
                    }

            });

            sb_groc.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progressValue;
                int totalPercentage;

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressValue = progress;
                    tv_groc.setText(String.valueOf(progress));
                    totalPercentage = sb_fresh.getProgress()+sb_groc.getProgress()+sb_bev.getProgress()+sb_house.getProgress()+sb_PCare.getProgress()+sb_cloth.getProgress();

                    if (progressValue<100)
                    {
                        if (totalPercentage>=100)
                        {
                            int max = 100 - (sb_fresh.getProgress()+sb_PCare.getProgress()+sb_bev.getProgress()+sb_house.getProgress()+sb_cloth.getProgress());
                            sb_groc.setProgress(max);
                        }

                        sb_fresh.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_fresh.setMax(100);

                        sb_house.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_house.setMax(100);

                        sb_bev.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_bev.setMax(100);

                        sb_PCare.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_PCare.setMax(100);

                        sb_cloth.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_cloth.setMax(100);
                    }
                    else  {
                        sb_fresh.setMax(0);
                        sb_fresh.clearAnimation();
                        sb_fresh.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_house.setMax(0);
                        sb_house.clearAnimation();
                        sb_house.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_bev.setMax(0);
                        sb_bev.clearAnimation();
                        sb_bev.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_PCare.setMax(0);
                        sb_PCare.clearAnimation();
                        sb_PCare.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_cloth.setMax(0);
                        sb_cloth.clearAnimation();
                        sb_cloth.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    tv_groc.setText(String.valueOf(progressValue));
                    grocBud.setText("RM "+(String.format("%.2f",(progressValue/100)*Float.parseFloat(ed_bud.getText().toString()))));
                }
            });

            sb_house.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progressValue;
                int totalPercentage;

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressValue = progress;
                    tv_house.setText(String.valueOf(progress));
                    totalPercentage = sb_fresh.getProgress()+sb_groc.getProgress()+sb_bev.getProgress()+sb_house.getProgress()+sb_PCare.getProgress()+sb_cloth.getProgress();

                    if (progressValue<100)
                    {
                        if (totalPercentage>=100)
                        {
                            int max = 100 - (sb_fresh.getProgress()+sb_groc.getProgress()+sb_bev.getProgress()+sb_PCare.getProgress()+sb_cloth.getProgress());
                            sb_house.setProgress(max);
                        }

                        sb_groc.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_groc.setMax(100);

                        sb_fresh.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_fresh.setMax(100);

                        sb_bev.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_bev.setMax(100);

                        sb_PCare.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_PCare.setMax(100);

                        sb_cloth.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_cloth.setMax(100);
                    }
                    else  {
                        sb_groc.setMax(0);
                        sb_groc.clearAnimation();
                        sb_groc.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_fresh.setMax(0);
                        sb_fresh.clearAnimation();
                        sb_fresh.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_bev.setMax(0);
                        sb_bev.clearAnimation();
                        sb_bev.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_PCare.setMax(0);
                        sb_PCare.clearAnimation();
                        sb_PCare.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_cloth.setMax(0);
                        sb_cloth.clearAnimation();
                        sb_cloth.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    tv_house.setText(String.valueOf(progressValue));
                    houseBud.setText("RM "+(String.format("%.2f",(progressValue/100)*Float.parseFloat(ed_bud.getText().toString()))));
                }
            });

            sb_bev.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progressValue;
                int totalPercentage;

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressValue = progress;
                    tv_bev.setText(String.valueOf(progress));
                    totalPercentage = sb_fresh.getProgress()+sb_groc.getProgress()+sb_bev.getProgress()+sb_house.getProgress()+sb_PCare.getProgress()+sb_cloth.getProgress();

                    if (progressValue<100)
                    {
                        if (totalPercentage>=100)
                        {
                            int max = 100 - (sb_fresh.getProgress()+sb_groc.getProgress()+sb_PCare.getProgress()+sb_house.getProgress()+sb_cloth.getProgress());
                            sb_bev.setProgress(max);
                        }

                        sb_groc.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_groc.setMax(100);

                        sb_house.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_house.setMax(100);

                        sb_fresh.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_fresh.setMax(100);

                        sb_PCare.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_PCare.setMax(100);

                        sb_cloth.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_cloth.setMax(100);
                    }
                    else  {
                        sb_groc.setMax(0);
                        sb_groc.clearAnimation();
                        sb_groc.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_house.setMax(0);
                        sb_house.clearAnimation();
                        sb_house.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_fresh.setMax(0);
                        sb_fresh.clearAnimation();
                        sb_fresh.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_PCare.setMax(0);
                        sb_PCare.clearAnimation();
                        sb_PCare.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_cloth.setMax(0);
                        sb_cloth.clearAnimation();
                        sb_cloth.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    tv_bev.setText(String.valueOf(progressValue));
                    bevBud.setText("RM "+(String.format("%.2f",(progressValue/100)*Float.parseFloat(ed_bud.getText().toString()))));
                }
            });

            sb_cloth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progressValue;
                int totalPercentage;

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressValue = progress;
                    tv_cloth.setText(String.valueOf(progress));
                    totalPercentage = sb_fresh.getProgress()+sb_groc.getProgress()+sb_bev.getProgress()+sb_house.getProgress()+sb_PCare.getProgress()+sb_cloth.getProgress();

                    if (progressValue<100)
                    {
                        if (totalPercentage>=100)
                        {
                            int max = 100 - (sb_fresh.getProgress()+sb_groc.getProgress()+sb_bev.getProgress()+sb_house.getProgress()+sb_PCare.getProgress());
                            sb_cloth.setProgress(max);
                        }

                        sb_groc.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_groc.setMax(100);

                        sb_house.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_house.setMax(100);

                        sb_bev.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_bev.setMax(100);

                        sb_PCare.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_PCare.setMax(100);

                        sb_fresh.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_fresh.setMax(100);
                    }
                    else  {
                        sb_groc.setMax(0);
                        sb_groc.clearAnimation();
                        sb_groc.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_house.setMax(0);
                        sb_house.clearAnimation();
                        sb_house.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_bev.setMax(0);
                        sb_bev.clearAnimation();
                        sb_bev.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_PCare.setMax(0);
                        sb_PCare.clearAnimation();
                        sb_PCare.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_fresh.setMax(0);
                        sb_fresh.clearAnimation();
                        sb_fresh.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    tv_cloth.setText(String.valueOf(progressValue));
                    clothBud.setText("RM "+(String.format("%.2f",(progressValue/100)*Float.parseFloat(ed_bud.getText().toString()))));

                }
            });

            sb_PCare.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progressValue;
                int totalPercentage;

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressValue = progress;
                    tv_PCare.setText(String.valueOf(progress));
                    totalPercentage = sb_fresh.getProgress()+sb_groc.getProgress()+sb_bev.getProgress()+sb_house.getProgress()+sb_PCare.getProgress()+sb_cloth.getProgress();

                    if (progressValue<100)
                    {
                        if (totalPercentage>=100)
                        {
                            int max = 100 - (sb_fresh.getProgress()+sb_groc.getProgress()+sb_bev.getProgress()+sb_house.getProgress()+sb_cloth.getProgress());
                            sb_PCare.setProgress(max);
                        }

                        sb_groc.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_groc.setMax(100);

                        sb_house.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_house.setMax(100);

                        sb_bev.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_bev.setMax(100);

                        sb_fresh.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_fresh.setMax(100);

                        sb_cloth.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        sb_cloth.setMax(100);
                    }
                    else  {
                        sb_groc.setMax(0);
                        sb_groc.clearAnimation();
                        sb_groc.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_house.setMax(0);
                        sb_house.clearAnimation();
                        sb_house.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_bev.setMax(0);
                        sb_bev.clearAnimation();
                        sb_bev.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_fresh.setMax(0);
                        sb_fresh.clearAnimation();
                        sb_fresh.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        sb_cloth.setMax(0);
                        sb_cloth.clearAnimation();
                        sb_cloth.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    tv_PCare.setText(String.valueOf(progressValue));
                    PCBud.setText("RM "+(String.format("%.2f",(progressValue/100)*Float.parseFloat(ed_bud.getText().toString()))));
                }
            });


        GoShopBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ed_bud.getText().toString().length()!=0){
                        Float budget = Float.parseFloat(ed_bud.getText().toString());

                        if(preferences.getDataTotalCart(Budget.this).isEmpty())
                        {
                            preferences.setDataTotalCart(Budget.this,"0");
                        }
                        Float totalCart = Float.parseFloat(preferences.getDataTotalCart(Budget.this));

                        preferences.setDataBudget(Budget.this, ed_bud.getText().toString());
                        if(tv_fresh.getText().toString().matches("0"))
                            preferences.setDataFreshBudget(Budget.this, String.valueOf(0));
                        else
                            preferences.setDataFreshBudget(Budget.this, tv_fresh.getText().toString());
                        if(tv_groc.getText().toString().matches("0"))
                            preferences.setDataGroBudget(Budget.this, String.valueOf(0));
                        else
                            preferences.setDataGroBudget(Budget.this, tv_groc.getText().toString());
                        if(tv_bev.getText().toString().matches("0"))
                            preferences.setDataBevBudget(Budget.this, String.valueOf(0));
                        else
                            preferences.setDataBevBudget(Budget.this, tv_bev.getText().toString());
                        if(tv_house.getText().toString().matches("0"))
                            preferences.setDataHouseBudget(Budget.this, String.valueOf(0));
                        else
                            preferences.setDataHouseBudget(Budget.this, tv_house.getText().toString());
                        if(tv_PCare.getText().toString().matches("0"))
                            preferences.setDataPCareBudget(Budget.this, String.valueOf(0));
                        else
                            preferences.setDataPCareBudget(Budget.this, tv_PCare.getText().toString());
                        if(tv_cloth.getText().toString().matches("0"))
                            preferences.setDataClothBudget(Budget.this, String.valueOf(0));
                        else
                            preferences.setDataClothBudget(Budget.this, tv_cloth.getText().toString());

                        if(preferences.getDataFreshBudgetTotal(Budget.this).isEmpty())
                            preferences.setDataFreshBudgetTotal(Budget.this, String.valueOf(0));
                        if(preferences.getDataBevBudgetTotal(Budget.this).isEmpty())
                            preferences.setDataBevBudgetTotal(Budget.this, String.valueOf(0));
                        if(preferences.getDataGroBudgetTotal(Budget.this).isEmpty())
                            preferences.setDataGroBudgetTotal(Budget.this, String.valueOf(0));
                        if(preferences.getDataHouseBudgetTotal(Budget.this).isEmpty())
                            preferences.setDataHouseBudgetTotal(Budget.this, String.valueOf(0));
                        if(preferences.getDataPCareBudgetTotal(Budget.this).isEmpty())
                            preferences.setDataPCareBudgetTotal(Budget.this, String.valueOf(0));
                        if(preferences.getDataClothBudgetTotal(Budget.this).isEmpty())
                            preferences.setDataClothBudgetTotal(Budget.this, String.valueOf(0));


                        Float freshBud = Float.parseFloat(preferences.getDataFreshBudget(Budget.this));
                        Float groBud = Float.parseFloat(preferences.getDataGroBudget(Budget.this));
                        Float bevBud = Float.parseFloat(preferences.getDataBevBudget(Budget.this));
                        Float houseBud = Float.parseFloat(preferences.getDataHouseBudget(Budget.this));
                        Float PCareBud = Float.parseFloat(preferences.getDataPCareBudget(Budget.this));
                        Float clothBud = Float.parseFloat(preferences.getDataClothBudget(Budget.this));

                        Float fresh = (freshBud/100)*budget;
                        Float groc = (groBud/100)*budget;
                        Float bev = (bevBud/100)*budget;
                        Float house = (houseBud/100)*budget;
                        Float pCare = (PCareBud/100)*budget;
                        Float cloth = (clothBud/100)*budget;

                        if(budget<totalCart)
                        {
                            Toast.makeText(Budget.this, "Budget not enough, increase your budget",Toast.LENGTH_SHORT).show();
                        }
                        else if(fresh<Float.parseFloat(preferences.getDataFreshBudgetTotal(Budget.this)))
                        {
                            Toast.makeText(Budget.this, "Fresh Budget not enough, increase your budget",Toast.LENGTH_SHORT).show();
                        }
                        else if(groc<Float.parseFloat(preferences.getDataGroBudgetTotal(Budget.this)))
                        {
                            Toast.makeText(Budget.this, "Groceries Budget not enough, increase your budget",Toast.LENGTH_SHORT).show();
                        }
                        else if(bev<Float.parseFloat(preferences.getDataBevBudgetTotal(Budget.this)))
                        {
                            Toast.makeText(Budget.this, "Beverages Budget not enough, increase your budget",Toast.LENGTH_SHORT).show();
                        }
                        else if(house<Float.parseFloat(preferences.getDataHouseBudgetTotal(Budget.this)))
                        {
                            Toast.makeText(Budget.this, "Household Budget not enough, increase your budget",Toast.LENGTH_SHORT).show();
                        }
                        else if(pCare<Float.parseFloat(preferences.getDataPCareBudgetTotal(Budget.this)))
                        {
                            Toast.makeText(Budget.this, "Personal Care Budget not enough, increase your budget",Toast.LENGTH_SHORT).show();
                        }
                        else if(cloth<Float.parseFloat(preferences.getDataClothBudgetTotal(Budget.this)))
                        {
                            Toast.makeText(Budget.this, "Clothes Budget not enough, increase your budget",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent goShopping = new Intent(Budget.this, ShoppingPage.class);
                            startActivity(goShopping);
                        }
                    }else
                        Toast.makeText(Budget.this, "Enter your budget",Toast.LENGTH_SHORT).show();


                }
            });
    }

    @Override
    protected void onResume() {
        super.onResume();

        ed_bud.setText(String.valueOf(preferences.getDataBudget(this)));
        tv_fresh.setText(String.valueOf(preferences.getDataFreshBudget(this)));
        tv_groc.setText(String.valueOf(preferences.getDataGroBudget(this)));
        tv_house.setText(String.valueOf(preferences.getDataHouseBudget(this)));
        tv_bev.setText(String.valueOf(preferences.getDataBevBudget(this)));
        tv_PCare.setText(String.valueOf(preferences.getDataPCareBudget(this)));
        tv_cloth.setText(String.valueOf(preferences.getDataClothBudget(this)));

        if (preferences.getDataFreshBudget(this).isEmpty())
        {
            preferences.setDataFreshBudget(this,"0");
            tv_fresh.setText("0");
        }
        if (preferences.getDataGroBudget(this).isEmpty())
        {
            preferences.setDataGroBudget(this,"0");
            tv_groc.setText("0");
        }
        if (preferences.getDataBevBudget(this).isEmpty())
        {
            preferences.setDataBevBudget(this,"0");
            tv_bev.setText("0");
        }
        if (preferences.getDataHouseBudget(this).isEmpty())
        {
            preferences.setDataHouseBudget(this,"0");
            tv_house.setText("0");
        }
        if (preferences.getDataPCareBudget(this).isEmpty())
        {
            preferences.setDataPCareBudget(this,"0");
            tv_PCare.setText("0");
        }
        if (preferences.getDataClothBudget(this).isEmpty())
        {
            preferences.setDataClothBudget(this,"0");
            tv_cloth.setText("0");
        }

        sb_fresh.setProgress(Integer.parseInt(preferences.getDataFreshBudget(this)));
        sb_groc.setProgress(Integer.parseInt(preferences.getDataGroBudget(this)));
        sb_house.setProgress(Integer.parseInt(preferences.getDataHouseBudget(this)));
        sb_bev.setProgress(Integer.parseInt(preferences.getDataBevBudget(this)));
        sb_PCare.setProgress(Integer.parseInt(preferences.getDataPCareBudget(this)));
        sb_cloth.setProgress(Integer.parseInt(preferences.getDataClothBudget(this)));

    }

}