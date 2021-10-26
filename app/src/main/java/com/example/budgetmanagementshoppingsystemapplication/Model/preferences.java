package com.example.budgetmanagementshoppingsystemapplication.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class preferences {
    private static final String DATA_LOGIN = "status_login",
            DATA_STATUS = "username",
            DATA_USERID = "userid",
            DATA_CUSTNAME = "custName",
            DATA_BUDGET = "budget",
            DATA_FRESHBUDGET = "freshBudget",
            DATA_GROBUDGET = "groBudget",
            DATA_BEVBUDGET = "bevBudget",
            DATA_HOUSEBUDGET = "houseBudget",
            DATA_PCAREBUDGET = "pCareBudget",
            DATA_CLOTHBUDGET = "clothBudget",
            DATA_FRESHBUDGETTOTAL = "freshBudgetTotal",
            DATA_GROBUDGETTOTAL = "groBudgetTotal",
            DATA_BEVBUDGETTOTAL = "bevBudgetTotal",
            DATA_HOUSEBUDGETTOTAL = "houseBudgetTotal",
            DATA_PCAREBUDGETTOTAL = "pCareBudgetTotal",
            DATA_CLOTHBUDGETTOTAL = "clothBudgetTotal",
            DATA_TOTALCART = "totalCart";


    private static SharedPreferences getSharedReferences(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setDataBudget(Context context, String budget)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_BUDGET,budget);
        editor.apply();
    }

    public static String getDataBudget(Context context)
    {
        return getSharedReferences(context).getString(DATA_BUDGET,"");
    }

    public static void setDataTotalCart(Context context, String totalCart)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_TOTALCART,totalCart);
        editor.apply();
    }

    public static String getDataTotalCart(Context context)
    {
        return getSharedReferences(context).getString(DATA_TOTALCART,"");
    }


    public static void setDataFreshBudgetTotal(Context context, String freshBudgetTotal)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_FRESHBUDGETTOTAL,freshBudgetTotal);
        editor.apply();
    }

    public static String getDataFreshBudgetTotal(Context context)
    {
        return getSharedReferences(context).getString(DATA_FRESHBUDGETTOTAL,"");
    }

    public static void setDataGroBudgetTotal(Context context, String budgetGroTotal)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_GROBUDGETTOTAL,budgetGroTotal);
        editor.apply();
    }

    public static String getDataGroBudgetTotal(Context context)
    {
        return getSharedReferences(context).getString(DATA_GROBUDGETTOTAL,"");
    }

    public static void setDataBevBudgetTotal(Context context, String budgetBevTotal)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_BEVBUDGETTOTAL,budgetBevTotal);
        editor.apply();
    }

    public static String getDataBevBudgetTotal(Context context)
    {
        return getSharedReferences(context).getString(DATA_BEVBUDGETTOTAL,"");
    }

    public static void setDataHouseBudgetTotal(Context context, String budgetHouseTotal)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_HOUSEBUDGETTOTAL,budgetHouseTotal);
        editor.apply();
    }

    public static String getDataHouseBudgetTotal(Context context)
    {
        return getSharedReferences(context).getString(DATA_HOUSEBUDGETTOTAL,"");
    }

    public static void setDataPCareBudgetTotal(Context context, String budgetPCareTotal)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_PCAREBUDGETTOTAL,budgetPCareTotal);
        editor.apply();
    }

    public static String getDataPCareBudgetTotal(Context context)
    {
        return getSharedReferences(context).getString(DATA_PCAREBUDGETTOTAL,"");
    }

    public static void setDataClothBudgetTotal(Context context, String budgetClothTotal)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_CLOTHBUDGETTOTAL,budgetClothTotal);
        editor.apply();
    }

    public static String getDataClothBudgetTotal(Context context)
    {
        return getSharedReferences(context).getString(DATA_CLOTHBUDGETTOTAL,"");
    }



    public static void setDataFreshBudget(Context context, String freshBudget)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_FRESHBUDGET,freshBudget);
        editor.apply();
    }

    public static String getDataFreshBudget(Context context)
    {
        return getSharedReferences(context).getString(DATA_FRESHBUDGET,"");
    }

    public static void setDataGroBudget(Context context, String budgetGro)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_GROBUDGET,budgetGro);
        editor.apply();
    }

    public static String getDataGroBudget(Context context)
    {
        return getSharedReferences(context).getString(DATA_GROBUDGET,"");
    }

    public static void setDataBevBudget(Context context, String budgetBev)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_BEVBUDGET,budgetBev);
        editor.apply();
    }

    public static String getDataBevBudget(Context context)
    {
        return getSharedReferences(context).getString(DATA_BEVBUDGET,"");
    }

    public static void setDataHouseBudget(Context context, String budgetHouse)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_HOUSEBUDGET,budgetHouse);
        editor.apply();
    }

    public static String getDataHouseBudget(Context context)
    {
        return getSharedReferences(context).getString(DATA_HOUSEBUDGET,"");
    }

    public static void setDataPCareBudget(Context context, String budgetPCare)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_PCAREBUDGET,budgetPCare);
        editor.apply();
    }

    public static String getDataPCareBudget(Context context)
    {
        return getSharedReferences(context).getString(DATA_PCAREBUDGET,"");
    }

    public static void setDataClothBudget(Context context, String budgetCloth)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_CLOTHBUDGET,budgetCloth);
        editor.apply();
    }

    public static String getDataClothBudget(Context context)
    {
        return getSharedReferences(context).getString(DATA_CLOTHBUDGET,"");
    }

    public static void setDataCustomerName(Context context, String custName)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_CUSTNAME,custName);
        editor.apply();
    }

    public static String getDataCustomerName(Context context)
    {
        return getSharedReferences(context).getString(DATA_CUSTNAME,"");
    }

    public static void setDataUserID(Context context, String userID)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_USERID,userID);
        editor.apply();
    }

    public static String getDataUserID(Context context)
    {
        return getSharedReferences(context).getString(DATA_USERID,"");
    }

    public static void setDataStatus(Context context, String data)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_STATUS,data);
        editor.apply();
    }

    public static String getDataStatus(Context context)
    {
        return getSharedReferences(context).getString(DATA_STATUS,"");
    }

    public static void setDataLogin(Context context, boolean status)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putBoolean(DATA_LOGIN,status);
        editor.apply();
    }

    public static boolean getDataLogin(Context context)
    {
        return getSharedReferences(context).getBoolean(DATA_LOGIN,false);
    }
    
    public static void clearData(Context context)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.remove(DATA_STATUS);
        editor.remove(DATA_LOGIN);
        editor.remove(DATA_USERID);
        editor.remove(DATA_CUSTNAME);
        editor.remove(DATA_BUDGET);
        editor.remove(DATA_FRESHBUDGET);
        editor.remove(DATA_GROBUDGET);
        editor.remove(DATA_BEVBUDGET);
        editor.remove(DATA_HOUSEBUDGET);
        editor.remove(DATA_PCAREBUDGET);
        editor.remove(DATA_CLOTHBUDGET);
        editor.apply();
    }
}
