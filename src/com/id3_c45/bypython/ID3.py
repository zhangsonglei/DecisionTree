from treePlotter import *
import operator
from math import log

# load data about lense.
def loadData(path):
    fr = open(path)
    lense = [example.strip().split("\t") for example in fr.readlines()]
    features = ["age", "typeOfLense", "astigmatic", "tearRate"]
    return lense, features

# calc the parameter of dataset.
def getDataSetEnt(dataSet):
    numEntries = len(dataSet)
    classCounts = {}
    for featVec in dataSet:
        # get the label.let it be str.
        currentLabel = featVec[-1]
        if currentLabel not in list(classCounts.keys()):
            classCounts[currentLabel] = 0
        classCounts[currentLabel] += 1
    shannonEnt = 0.0
    for key in classCounts:
        prob = float(classCounts[key]) / numEntries
        shannonEnt -= prob * log(prob, 2)
    return shannonEnt


# split data set by a value of feature and get the sub set.
# return,the returns is a list.when we get one of the returns,wo must through the syntax of list
def splitDataSetByFeature(dataSet, axis, value):
    retDataSet = []
    for featVec in dataSet:
        if featVec[axis] == value:
            # get the sub set and remove the feature used to split from the data set.
            reducedFeatVec = featVec[:axis]
            reducedFeatVec.extend(featVec[axis + 1:])
            retDataSet.append(reducedFeatVec)

    # p = float(len(retDataSet)) / len(dataSet)
    return retDataSet


# through the information gain to get the best feature to split.
# return,the index of current best feature.
def getBestFeatureByGain(dataSet):
    # has not considered the label.what's more,the length of features are changing.
    numFeatures = len(dataSet[0]) - 1
    baseEntropy = getDataSetEnt(dataSet)
    bestInfoGain = 0.0;
    bestFeature = -1
    for i in range(numFeatures):
        featList = [example[i] for example in dataSet]
        uniqueVals = set(featList)
        newEntropy = 0.0
        for value in uniqueVals:
            subDataSet = splitDataSetByFeature(dataSet, i, value)
            prob = len(subDataSet) / float(len(dataSet))
            newEntropy += prob * getDataSetEnt(subDataSet)
        infoGain = baseEntropy - newEntropy
        if (infoGain > bestInfoGain):
            bestInfoGain = infoGain
            bestFeature = i
    return bestFeature

# decide the label by majority.
def getLabelOfDataSet(classList):
    classCount = {}
    for vote in classList:
        if vote not in classCount.keys():
            classCount[vote] = 0
        classCount[vote] += 1
        # sort on number of every class and get the sorted index.
    count = 0
    flag = ''
    for i in classCount.keys():
        if classCount[i] > count:
            count = classCount[i]
            flag = i
    # sortedClassCount = sorted(classCount.iteritems(), key=operator.itemgetter(1), reverse=True)
    return flag


# through  recursive function  to create a decision tree stored by a dictionary.
def createDecisionTree(dataSet, labels):
    # classList is the label set of all data and is not the value set of label.
    classList = [example[-1] for example in dataSet]
    if classList.count(classList[0]) == len(classList):
        # get the leaf when all of the classes are equal.
        return classList[0], dataSet
    if len(dataSet[0]) == 1:
        # stop splitting when there are no more features in dataSet
        return getLabelOfDataSet(classList), dataSet

    bestFeat = getBestFeatureByGain(dataSet)
    bestFeatLabel = labels[bestFeat]
    myTree = {bestFeatLabel: {}}
    treeData = {bestFeatLabel: {}}
    # the reduce of label and data is uniform.
    del (labels[bestFeat])
    featValues = [example[bestFeat] for example in dataSet]
    uniqueVals = set(featValues)
    for value in uniqueVals:
        subLabels = labels[:]
        myTree[bestFeatLabel][value], mid = createDecisionTree(splitDataSetByFeature(dataSet, bestFeat, value),
                                                               subLabels)
        treeData[bestFeatLabel][value] = mid.copy()
    return myTree, treeData


def create_id3_model(self, path):
    data, features = loadData(path)
    tree, subSet = createDecisionTree(data, features)
    createPlot(tree)
