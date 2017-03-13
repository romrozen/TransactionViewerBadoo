package com.badoo.roman.badootransactionviewer;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import fragment.ChosenProductFragment;
import fragment.ProductsFragment;
import model.ConversionRateTo;
import model.SkuOverview;
import model.TransactionMeta;
import utils.Conversion;
import utils.LoadAssets;
import utils.Tools;

public class MainActivity extends AppCompatActivity implements ProductsFragment.OnListFragmentInteractionListener, ChosenProductFragment.onFragmentDetachedListener {

    private FragmentManager fragmentManager;
    private HashMap<String,ArrayList<TransactionMeta>> transactionsFromAssets;
    private HashMap<String,Double> currencyToGBP;
    private double totalAmountInGBP;
    private ActionBar actionBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.ProductsPageTitle);
        }

        fragmentManager = getSupportFragmentManager();

        new LoadAssets(this, new LoadAssets.AsyncResponse() {
            @Override
            public void processFinish(String output) {

                transactionsFromAssets = Tools.loadTransactionFromAssets(MainActivity.this,output);
                ArrayList<SkuOverview> transactionMetaArrayList = Tools.getArray(transactionsFromAssets);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ProductsFragment productsFragment = ProductsFragment.newInstance(transactionMetaArrayList);
                fragmentTransaction.add(R.id.fragment_container, productsFragment);
                fragmentTransaction.commit();

            }
        }).execute(getString(R.string.transactions_file_name));


        new LoadAssets(this, new LoadAssets.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                HashMap<String,ArrayList<ConversionRateTo>> currencyRatesFromAssets =  Tools.loadCurrencyRatesFromAssets(MainActivity.this,output);
                Conversion conversion = new Conversion(currencyRatesFromAssets);
                currencyToGBP = conversion.getCurrencyRateToGBPRate();

            }
        }).execute(getString(R.string.rates_file_name));

    }

    @Override
    public void onProductSelected(SkuOverview item) {

        addConvertedAmountsAndCalculateTotal(transactionsFromAssets.get(item.getSku()));

        if (actionBar != null) {
            actionBar.setTitle(String.format(Locale.getDefault(),"Transactions for %s",item.getSku()));
            actionBar.setSubtitle(String.format(Locale.getDefault(),"Total: %.2f(GBP)",totalAmountInGBP));
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ChosenProductFragment productsFragment = ChosenProductFragment.newInstance(transactionsFromAssets.get(item.getSku()));
        fragmentTransaction.replace(R.id.fragment_container, productsFragment).addToBackStack(null).commit();
    }

    private void addConvertedAmountsAndCalculateTotal(ArrayList<TransactionMeta> skuMeta) {
        for (TransactionMeta transactionMeta : skuMeta) {
            double amountInGBP = transactionMeta.getOriginalAmount() * currencyToGBP.get(transactionMeta.getCurrency());
            transactionMeta.setConvertedAmount(amountInGBP);
            totalAmountInGBP +=amountInGBP;
        }
    }

    @Override
    public void onFragmentDetach() {
        if (actionBar != null) {
            actionBar.setTitle(R.string.ProductsPageTitle);
            actionBar.setSubtitle("");
        }
        totalAmountInGBP = 0;
    }
}