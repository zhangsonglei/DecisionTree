package com.decisiontree.id3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * Description: to build a decision tree
 * @author: Sonly
 * Company: HUST 
 * @date: 2017年5月1日上午10:50:39
 */
public class DecisionTree {

	/**
	 * 
	 * @Title: buildTree
	 * Description: build decision tree
	 * @param dataSet
	 * @param attrSet
	 * @return TreeNode
	 * @throws
	 */
	public static TreeNode buildTree(ArrayList<ArrayList<String>> dataSet, ArrayList<String> featureSet) {
		TreeNode node = new TreeNode();
		node.setFeatureSet(featureSet);
		node.setDataSet(dataSet);
		
		/**
		 * get the index of feature which own max information gain
		 */
		int index = -1;
		double maxGain = 0.0;
		double H = InfoGain.getEntropy(dataSet, featureSet.size() - 1);
		for(int i = 1; i < featureSet.size() - 1; i++) {
			double gain =  H - InfoGain.getConditionEntropy(dataSet, i);
			if(maxGain < gain) {
				maxGain = gain;
				index = i;
			}
			//System.out.println("feature="+featureSet.get(i)+" gain="+gain);
		}
		
		/**
		 * add this feature and the data set belong to it to decision tree 
		 */
		ArrayList<String> featureVal = InfoGain.getFeatureVal(dataSet, index);
		node.setFeatureVal(featureVal);
		node.setNodeName(featureSet.get(index));
		
		/**
		 * split child node
		 */
		for(int i = 0; i < featureVal.size(); i++) {
			TreeNode childNode = new TreeNode();
			ArrayList<ArrayList<String>> childDataSet = InfoGain.getChildDataSet(dataSet, index, featureVal.get(i));
			int desIndex = childDataSet.get(0).size() - 1;
			ArrayList<String> desFeatureVal = InfoGain.getFeatureVal(childDataSet, desIndex);
			
			/**
			 * if 
			 *     desFeatureVal is pure, build end
			 * else
			 *     continue split
			 */
			if(1 == desFeatureVal.size()) 
				childNode.setNodeName(desFeatureVal.get(0));
			else {
				ArrayList<String> newFeatureSet = new ArrayList<>();
				for(String str : featureSet)
					if(!str.equals(featureSet.get(index)))
						newFeatureSet.add(str);
				
				ArrayList<ArrayList<String>> newDataSet = new ArrayList<ArrayList<String>>();
				for(ArrayList<String> data : childDataSet) {
					ArrayList<String> temp = new ArrayList<>();
					for(int k = 0; k < data.size(); k++)
						if(k != index)
							temp.add(data.get(k));
					
					newDataSet.add(temp);
				}
				
				childNode = buildTree(newDataSet, newFeatureSet);
			}
			
			node.getChild().add(childNode);
		}
		
		return node;
	}
	
	/**
	 * 
	 * @Title: searchDecisionTree
	 * Description: traverse decision tree by layer
	 * @param root
	 * @return void
	 * @throws
	 */
	public static void searchDecisionTree(TreeNode root) {
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		
		while(0 != queue.size()) {
			TreeNode node = queue.poll();
			
			if(null != node.getFeatureVal()) {
				System.out.println("split node:"+node.getNodeName());
				for(String value : node.getFeatureVal())
					System.out.print(" (" + value + ") ");
			}else
				System.out.print("leaf node:"+node.getNodeName());
			
			if(null != node.getChild())
				for(TreeNode tn : node.getChild())
					queue.offer(tn);
		}
	}
	
	/**
	 * 
	 * @Title: printDecisionTree
	 * Description: print the decision tree
	 * @param root
	 * @return void
	 * @throws
	 */
	public static void printDecisionTree(TreeNode root) {
		System.out.println("**************************");
		if(null != root.getFeatureVal()) {
			System.out.println("split node:"+root.getNodeName());
			for(String value : root.getFeatureVal())
				System.out.print(" (" + value + ") ");
		}else
			System.out.print("leaf node:"+root.getNodeName());
		
		if(null != root.getChild())
			for(TreeNode tn : root.getChild())
				printDecisionTree(tn);
	}
}
