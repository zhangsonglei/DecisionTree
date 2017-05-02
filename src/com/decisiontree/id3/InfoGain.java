package com.decisiontree.id3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Description: provide some compute method to class decision tree
 * @author: Sonly
 * Company: HUST 
 * @date: 2017年5月1日上午11:10:05
 */
public class InfoGain {

	/**
	 * 
	 * @Title: getAttrVal
	 * Description: get all features at index position in data set
	 * @param dataSet
	 * @param index
	 * @return ArrayList<String>
	 * @throws
	 */
	public static ArrayList<String> getFeatureVal(ArrayList<ArrayList<String>> dataSet, int index) {
		ArrayList<String> attrVal = new ArrayList<>();
		
		for(ArrayList<String> line : dataSet)
			if(!attrVal.contains(line.get(index)))
				attrVal.add(line.get(index));
		
		return attrVal;
	}
	
	/**
	 * 
	 * @Title: featureStatistic
	 * Description: statistics the value of each feature at the index position
	 * @param dataSet
	 * @param index
	 * @return HashMap<String,Integer>
	 * @throws
	 */
	public static HashMap<String, Integer> featureStatistic(ArrayList<ArrayList<String>> dataSet, int index) {
		HashMap<String, Integer> feature = new HashMap<>();
		
		for(ArrayList<String> line: dataSet) {
			if(feature.containsKey(line.get(index)))
				feature.put(line.get(index), feature.get(line.get(index)) + 1);
			else 
				feature.put(line.get(index), 1);
		}
		
		return feature;
	}
	
	/**
	 * 
	 * @Title: getChildDataSet
	 * Description: collect data whose feature value is featureVal at index from data set
	 * @param dataSet
	 * @param index
	 * @param featureVal
	 * @return ArrayList<ArrayList<String>>
	 * @throws
	 */
	public static ArrayList<ArrayList<String>> getChildDataSet(ArrayList<ArrayList<String>> dataSet, 
																int index, String featureVal) {
		ArrayList<ArrayList<String>> childDataSet = new ArrayList<ArrayList<String>>();
	
		for(ArrayList<String> data : dataSet)
			if(data.get(index).equals(featureVal))
				childDataSet.add(data);
	
		return childDataSet;
	}
	
	/**
	 * 
	 * @Title: getEntropy
	 * Description: compute the entropy of the feature at index 
	 * @param dataSet
	 * @param index
	 * @return double
	 * @throws
	 */
	public static double getEntropy(ArrayList<ArrayList<String>> dataSet, int index) {
		double entropy = 0.0;
		int total = dataSet.size();
		
		HashMap<String, Integer> hashMap = featureStatistic(dataSet, index);
		for(Map.Entry<String, Integer> entry:hashMap.entrySet()) {
			double p = (double)entry.getValue() / (double)total;
			entropy -= p * log2(p);
		}
		
		return entropy;
	}
	
	/**
	 * 
	 * @Title: getConditionEntropy
	 * Description: compute conditional Entropy of the feature at index
	 * @param dataSet
	 * @param index
	 * @return double
	 * @throws
	 */
	public static double getConditionEntropy(ArrayList<ArrayList<String>> dataSet, int index) {
		double conditionEntropy = 0.0;
		HashMap<String, Integer> hashMap = featureStatistic(dataSet, index);
		
		for(Map.Entry<String, Integer> entry : hashMap.entrySet()) {
			ArrayList<ArrayList<String>> childDataSet = getChildDataSet(dataSet, index, entry.getKey());
			int desIndex = childDataSet.get(0).size() > 0 ? childDataSet.get(0).size() - 1 : 0;
			HashMap<String, Integer> map = featureStatistic(childDataSet, desIndex);
			
			double featureP = (double)childDataSet.size() / (double)dataSet.size();
			double featureEntropy = 0.0;
			int total = childDataSet.size();
			for(Map.Entry<String, Integer> entry2 : map.entrySet()) {
				double p = (double)entry2.getValue() / total;
				featureEntropy -= p * log2(p);
			}
			
			conditionEntropy += featureP * featureEntropy;
		}
		
		return conditionEntropy;
	}
	
	/**
	 * 
	 * @Title: log
	 * Description: compute the value of log(a) whose base is b
	 * @param a
	 * @param b
	 * @return double
	 * @throws
	 */
	public static double log2(double a) {
		return Math.log(a)/Math.log(2.0);
	}
	
}
