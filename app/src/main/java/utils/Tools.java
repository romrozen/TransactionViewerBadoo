package utils;

import android.content.Context;

import com.badoo.roman.badootransactionviewer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import model.ConversionRateTo;
import model.SkuOverview;
import model.TransactionMeta;

/**
 * Created by Roman on 13-Mar-17.
 */

public class Tools {


    static public HashMap<String,ArrayList<TransactionMeta>> loadTransactionFromAssets(Context context, String jsonArray) {
        HashMap<String,ArrayList<TransactionMeta>> trHashMap= new HashMap<>();
        try {
            JSONArray m_jArry = new JSONArray(jsonArray);

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject transactionJson = m_jArry.getJSONObject(i);
                String sku = null;
                double amount = 0;
                String currency = null;
                if (transactionJson.has(context.getString(R.string.sku))) {
                    sku = transactionJson.getString(context.getString(R.string.sku));
                }
                if(transactionJson.has(context.getString(R.string.amount))) {
                    amount = transactionJson.getDouble(context.getString(R.string.amount));
                }
                if(transactionJson.has(context.getString(R.string.currency))) {
                    currency = transactionJson.getString(context.getString(R.string.currency));
                }
                if (!trHashMap.containsKey(sku)){
                    ArrayList<TransactionMeta> transactionMetas = new ArrayList<>();
                    transactionMetas.add(new TransactionMeta(amount,currency));
                    trHashMap.put(sku,transactionMetas);
                }else{
                    trHashMap.get(sku).add(new TransactionMeta(amount,currency));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trHashMap;
    }


    static public LinkedHashMap<String,ArrayList<ConversionRateTo>> loadCurrencyRatesFromAssets(Context context, String jsonArray) {
        LinkedHashMap<String,ArrayList<ConversionRateTo>> trHashMap= new LinkedHashMap<>();
        try {
            JSONArray m_jArry = new JSONArray(jsonArray);

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject ratesJson = m_jArry.getJSONObject(i);
                String sourceCurrency = null;
                double rate = 0;
                String targetCurrency = null;
                if(ratesJson.has(context.getString(R.string.assets_from))) {
                    sourceCurrency = ratesJson.getString(context.getString(R.string.assets_from));
                }

                if(ratesJson.has(context.getString(R.string.assets_rate))) {
                    rate = ratesJson.getDouble(context.getString(R.string.assets_rate));
                }

                if(ratesJson.has(context.getString(R.string.assets_to))) {
                    targetCurrency = ratesJson.getString(context.getString(R.string.assets_to));
                }
                if (!trHashMap.containsKey(sourceCurrency)){
                    ArrayList<ConversionRateTo> currencyRates = new ArrayList<>();
                    currencyRates.add(new ConversionRateTo(targetCurrency,rate));
                    trHashMap.put(sourceCurrency,currencyRates);
                }else{
                    trHashMap.get(sourceCurrency).add(new ConversionRateTo(targetCurrency,rate));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trHashMap;
    }



    static public ArrayList<SkuOverview> getArray(HashMap hp) {
        ArrayList<SkuOverview> transactionMetaArrayList = new ArrayList<>();

        for (Object o : hp.entrySet()) {
            HashMap.Entry pair = (HashMap.Entry) o;
            transactionMetaArrayList.add(new SkuOverview((String) pair.getKey(), ((ArrayList<TransactionMeta>) pair.getValue()).size()));
        }
        return transactionMetaArrayList;
    }

}
