package com.decisiontree.id3;

import java.util.ArrayList;

/**
 * Node of Decision Tree
 * Description: 
 * @author: Sonly
 * Company: HUST 
 * @date: 2017年5月1日上午10:16:57
 */
public class TreeNode {
	private String nodeName;				//the feature to be split
	private ArrayList<String> featureVal;		//values of current feature
	private ArrayList<String> featureSet;		//features of data set
	private ArrayList<TreeNode> child;		//child node set
	private ArrayList<ArrayList<String>> dataSet;	//the train data set belong to nodeName
	
	/**
	 * 
	 * constructor
	 * initialize TreeNode class
	 */
	public TreeNode() {
		this.nodeName = "";
		this.featureVal = new ArrayList<String>();
		this.featureSet = null;
		this.child = new ArrayList<TreeNode>();
		this.dataSet = null;
	}
	
	public ArrayList<TreeNode> getChild() {
		return child;
	}
	
	public void setChild(ArrayList<TreeNode> child) {
		this.child = child;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public ArrayList<String> getFeatureVal() {
		return featureVal;
	}

	public void setFeatureVal(ArrayList<String> featureVal) {
		this.featureVal = featureVal;
	}

	public ArrayList<String> getFeatureSet() {
		return featureSet;
	}

	public void setFeatureSet(ArrayList<String> featureSet) {
		this.featureSet = featureSet;
	}

	public ArrayList<ArrayList<String>> getDataSet() {
		return dataSet;
	}

	public void setDataSet(ArrayList<ArrayList<String>> dataSet) {
		this.dataSet = dataSet;
	}
	
}
