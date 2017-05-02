package com.decisiontree.id3;

import java.util.ArrayList;

/**
 * 
 * Description: 
 * @author: Sonly
 * Company: HUST 
 * @date: 2017年5月1日上午10:16:32
 */
public class ID3 {
	public static void main(String[] args) {
		String filePath = "TrainData\\dataSet.utf8";
		ArrayList<ArrayList<String>> dataSet = new ArrayList<>();
		ArrayList<String> attrSet = new ArrayList<>();
		
		dataSet = FileOperater.readFile(filePath);
		attrSet = dataSet.get(0);
		dataSet.remove(0);
		
		TreeNode treeNode = DecisionTree.buildTree(dataSet, attrSet);
		
		DecisionTree.searchDecisionTree(treeNode);
	}

}
