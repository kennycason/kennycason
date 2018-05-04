---
title: Haskell - Neural Network Back-Error Propagation
author: Kenny Cason
tags: haskell, functional programming, artificial intelligence, λ\=
---

After going through various tutorials I decided to try and build something a bit more complicated. I decided to convert my
<a href="/posts/2008-12-25-neural-network-back-error-propagation-java.html" target="_new">Java implementation of a Back-Error Propagation Neural Network</a> into Haskell. There appears to be a small bug somewhere in the calculations...

I uploaded most of my Haskell examples to GitHub, found <a href="https://github.com/kennycason/haskell_nn" target="_new">here</a>

***Main.hs***

```haskell
import NN
import Utils
import Node
import Layer


testInput nn trainInput = do
    print (getOutput
                (feedForward
                    (setInput
                         nn trainInput)))


train trainInput teacherSignals = do
    -- print "create NN and train 100 steps"
    let nn = (trainStep nnNew trainInput teacherSignals 3000)
                where nnNew = setInput (createNN 2 10 1 2.5) trainInput

    -- print nn
    testInput nn trainInput

main = do
    -- only training one set of data at a time...
    print "testing values [1.0, 1.0] => 1.0"
    train [1.0, 1.0] [1.0]

    print "testing values [0.0, 0.0] => 0.0"
    train [0.0, 0.0] [0.0]

    print "testing values [1.0, 0.0] => 0.0"
    train [1.0, 0.0] [0.0]

    print "testing values [0.0, 1.0] => 0.0"
    train [0.0, 1.0] [0.0]
```

This yields the following, incorrect, but close output:

```
"testing values [1.0, 1.0] => 1.0"
"testing values [1.0, 1.0] => 1.0"
[0.9834379896449783]
"testing values [0.0, 0.0] => 0.0"
[0.9241418199787566]
"testing values [1.0, 0.0] => 0.0"
[0.5027090669395176]
"testing values [0.0, 1.0] => 0.0"
[0.5027090669395176]
```

***Node.hs***

```haskell
module Node
    (Node(..)
    ,numWeights
    ,createNode
    ,compareNode
    ,sigmoidNodeValue
    ,clearNodeValue
    )
where

import Utils

data Node = Node { value::Double, weights::[Double] } deriving Show

-- sigmoidNodeValue()
sigmoidNodeValue :: Node -> Node
sigmoidNodeValue node = node { value = sigmoid (value node) }


-- clearNodeValue()
clearNodeValue :: Node -> Node
clearNodeValue node = Node 0.0 (weights node)


-- createNode()
createNode :: Int -> Double -> Node
createNode numNodes defaultWeight = Node {
                                        value = 0.0
                                        ,weights = replicate numNodes defaultWeight
                                        }


-- numWeights()
numWeights :: Node -> Int
numWeights node = length (weights node)


-- compareNode()
compareNode :: Node -> Node -> Double
compareNode n1 n2 = abs ((value n2) - (value n1))
```

***Layer.hs***

```haskell
module Layer
    (Layer(..)
    ,createLayer
    ,createEmptyLayer
    ,calculateErrors
    ,calculateOutputErrors
    ,adjustWeights
    ,clearLayerValues
    ,calculateNodeValues
    ,sigmoidLayerValues
    ,isOutputLayer
    ,getErrors
)
where

import Utils
import Node

data Layer = Layer {  
                nodes :: [Node]
                ,errors :: [Double]
                ,teacherSignals :: [Double]
                ,learningRate :: Double
            } deriving Show


createNodeRow :: Int -> Int -> [Node]
createNodeRow numNodes numWeightsPerNode = replicate numNodes (createNode numWeightsPerNode 0.5)


createLayer :: Int -> Int -> Double -> Layer
createLayer numNodes numWeightsPerNode theLearningRate =
        Layer {
              nodes = (createNodeRow numNodes numWeightsPerNode)
              ,errors = (replicate numNodes 0.0)
              ,teacherSignals = (replicate numNodes 0.0)
              ,learningRate = theLearningRate
        }


createEmptyLayer = createLayer 0 0 0

-- calculateErrors()
sumError :: Node -> Layer -> Double
sumError node childLayer = sum (zipWith (*) (errors childLayer) (weights node))

calculateNodeErrors :: Node -> Layer -> Double
calculateNodeErrors node childLayer = (sumError node childLayer) * (value node) * (1.0 - (value node))

calculateErrors :: Layer -> Layer -> Layer
calculateErrors layer childLayer | isOutputLayer layer = calculateOutputErrors layer
                                 | otherwise = layer {
                                            errors = map (\node -> calculateNodeErrors node childLayer) (nodes layer)
                                        }


-- calculateOutputErrors()
calculateOutputNodeError :: Node -> Double -> Double
calculateOutputNodeError node teacherSignal =
                                (teacherSignal - (value node)) * (value node) * (1.0 - (value node))

calculateOutputErrors :: Layer -> Layer
calculateOutputErrors layer = layer {
                                errors = zipWith (\node teacherSignal ->
                                                        calculateOutputNodeError node teacherSignal)
                                                                                     (nodes layer)
                                                                                     (teacherSignals layer)
                            }


-- adjustWeights()
adjustWeightValue :: Double -> Double -> Double -> Double -> Double
adjustWeightValue value weight error learningRate =  weight + (learningRate * error * value)

adjustNodeWeight :: Node -> Layer -> Double -> Node
adjustNodeWeight node childLayer learningRate = node {
                                                 weights = zipWith
                                                      (\weight error ->
                                                              adjustWeightValue (value node) weight error learningRate)
                                                                            (weights node)
                                                                            (errors childLayer)
                                               }

adjustWeights :: Layer -> Layer -> Layer
adjustWeights layer childLayer = layer {
                                    nodes = map (\node -> adjustNodeWeight
                                                                    node
                                                                    childLayer
                                                                    (learningRate layer))
                                                                                   (nodes layer)
                                }


-- clearAllValues()
clearLayerValues :: Layer -> Layer
clearLayerValues layer = layer { nodes = (map clearNodeValue (nodes layer)) }


-- calculateNodeValues()
sumOfWeightsValues :: Layer -> [Double]
sumOfWeightsValues layer = foldl1 (zipWith (+))
                               [multConstList (value node) (weights node)
                               | node <- (nodes layer)]

updateChildNodeValue :: Double -> Node -> Node
updateChildNodeValue weightedValue childNode = childNode {
                                                value = weightedValue
                                             }

calculateNodeValues :: Layer -> Layer -> Layer
calculateNodeValues layer childLayer = childLayer {
                                        nodes = zipWith
                                                    updateChildNodeValue
                                                            (sumOfWeightsValues layer)
                                                            (nodes childLayer)
                                     }

-- sigmoidLayerValues()
sigmoidLayerValues :: Layer -> Layer
sigmoidLayerValues layer = layer { nodes = map (\node -> sigmoidNodeValue node) (nodes layer) }

-- isOutputLayer()
isOutputLayer :: Layer -> Bool
isOutputLayer layer = null (weights (getFirstNode layer))


-- getFirstNode()
getFirstNode :: Layer -> Node
getFirstNode layer = head (nodes layer)

-- getErrors()
getErrors :: Layer -> [Double]
getErrors layer = (errors layer)
```

***NN.hs***

```haskell
module Layer
    (Layer(..)
    ,createLayer
    ,createEmptyLayer
    ,calculateErrors
    ,calculateOutputErrors
    ,adjustWeights
    ,clearLayerValues
    ,calculateNodeValues
    ,sigmoidLayerValues
    ,isOutputLayer
    ,getErrors
)
where

import Utils
import Node

data Layer = Layer {  
                nodes :: [Node]
                ,errors :: [Double]
                ,teacherSignals :: [Double]
                ,learningRate :: Double
            } deriving Show


createNodeRow :: Int -> Int -> [Node]
createNodeRow numNodes numWeightsPerNode = replicate numNodes (createNode numWeightsPerNode 0.5)


createLayer :: Int -> Int -> Double -> Layer
createLayer numNodes numWeightsPerNode theLearningRate =
        Layer {
              nodes = (createNodeRow numNodes numWeightsPerNode)
              ,errors = (replicate numNodes 0.0)
              ,teacherSignals = (replicate numNodes 0.0)
              ,learningRate = theLearningRate
        }


createEmptyLayer = createLayer 0 0 0

-- calculateErrors()
sumError :: Node -> Layer -> Double
sumError node childLayer = sum (zipWith (*) (errors childLayer) (weights node))

calculateNodeErrors :: Node -> Layer -> Double
calculateNodeErrors node childLayer = (sumError node childLayer) * (value node) * (1.0 - (value node))

calculateErrors :: Layer -> Layer -> Layer
calculateErrors layer childLayer | isOutputLayer layer = calculateOutputErrors layer
                                 | otherwise = layer {
                                            errors = map (\node -> calculateNodeErrors node childLayer) (nodes layer)
                                        }


-- calculateOutputErrors()
calculateOutputNodeError :: Node -> Double -> Double
calculateOutputNodeError node teacherSignal =
                                (teacherSignal - (value node)) * (value node) * (1.0 - (value node))

calculateOutputErrors :: Layer -> Layer
calculateOutputErrors layer = layer {
                                errors = zipWith (\node teacherSignal ->
                                                        calculateOutputNodeError node teacherSignal)
                                                                                     (nodes layer)
                                                                                     (teacherSignals layer)
                            }


-- adjustWeights()
adjustWeightValue :: Double -> Double -> Double -> Double -> Double
adjustWeightValue value weight error learningRate =  weight + (learningRate * error * value)

adjustNodeWeight :: Node -> Layer -> Double -> Node
adjustNodeWeight node childLayer learningRate = node {
                                                 weights = zipWith
                                                      (\weight error ->
                                                              adjustWeightValue (value node) weight error learningRate)
                                                                            (weights node)
                                                                            (errors childLayer)
                                               }

adjustWeights :: Layer -> Layer -> Layer
adjustWeights layer childLayer = layer {
                                    nodes = map (\node -> adjustNodeWeight
                                                                    node
                                                                    childLayer
                                                                    (learningRate layer))
                                                                                   (nodes layer)
                                }


-- clearAllValues()
clearLayerValues :: Layer -> Layer
clearLayerValues layer = layer { nodes = (map clearNodeValue (nodes layer)) }


-- calculateNodeValues()
sumOfWeightsValues :: Layer -> [Double]
sumOfWeightsValues layer = foldl1 (zipWith (+))
                               [multConstList (value node) (weights node)
                               | node <- (nodes layer)]

updateChildNodeValue :: Double -> Node -> Node
updateChildNodeValue weightedValue childNode = childNode {
                                                value = weightedValue
                                             }

calculateNodeValues :: Layer -> Layer -> Layer
calculateNodeValues layer childLayer = childLayer {
                                        nodes = zipWith
                                                    updateChildNodeValue
                                                            (sumOfWeightsValues layer)
                                                            (nodes childLayer)
                                     }

-- sigmoidLayerValues()
sigmoidLayerValues :: Layer -> Layer
sigmoidLayerValues layer = layer { nodes = map (\node -> sigmoidNodeValue node) (nodes layer) }

-- isOutputLayer()
isOutputLayer :: Layer -> Bool
isOutputLayer layer = null (weights (getFirstNode layer))


-- getFirstNode()
getFirstNode :: Layer -> Node
getFirstNode layer = head (nodes layer)

-- getErrors()
getErrors :: Layer -> [Double]
getErrors layer = (errors layer)
```

***Utils.hs***

```haskell
module Utils
    (sigmoid
    ,listProduct
    ,listSquared
    ,listSum
    ,sumList
    ,multConstList
    ,addConstList
    )
where

-- sigmoid()
e = exp 1
sigmoid :: Double -> Double
sigmoid x = 1 / (1 + e**(-x))


-- listProduct()
listProduct a b = zipWith (*) a b


-- listSum()
listSum a b = zipWith (+) a b


-- listSquared()
listSquared :: [Double] -> [Double]
listSquared l = map (\n -> n * n) l


-- multConstList()
multConstList :: Double -> [Double] -> [Double]
multConstList const list = map (const *) list


-- addConstList()
addConstList :: Double -> [Double] -> [Double]
addConstList const list = map (const +) list


-- sumList()
sumList :: [Double] -> Double
sumList l = foldl (+) 0.0 l
```
