//
//  CalculatorBrain.swift
//  Calculator
//
//  Created by Fang Cao on 7/12/17.
//  Copyright © 2017 Entrepidea. All rights reserved.
//

import Foundation

func changeSign(digit : Double) -> Double {
    return -digit
}

//the Model component in MVC pattern
struct CalculatorBrain {
    
    private var accumulator : Double?
    private var accumulator1 : Double?
    
    private var binOperator : String?
    
    enum Operation{
        case constant(Double)
        case unaryOperation((Double) -> Double)
        case binaryOperation((Double, Double) -> Double)
    }
    
    
    
    var operations : Dictionary<String, Operation> = [
        "π":Operation.constant(Double.pi),
        "e":Operation.constant(M_E),
        "√":Operation.unaryOperation(sqrt),
        "cos":Operation.unaryOperation(cos),
        "±": Operation.unaryOperation({-$0}), //closure, you can also put changeSign here, but closure is obviously more succint.
        "×":Operation.binaryOperation({$0*$1}),
        "÷":Operation.binaryOperation({$0/$1}),
        "+":Operation.binaryOperation({$0+$1}),
        "−":Operation.binaryOperation({$0-$1})
    ]
    
    mutating func setOperand(_ operand : Double){
        accumulator = operand
    }
    
    
    mutating func setBinaryOperator(_ binaryOperator : String){
        binOperator = binaryOperator
        accumulator1 = accumulator
    }
    
    
    mutating func performOperand(_ mathSymbo: String){
        
        var operation: Operation?
        
        if mathSymbo == "=" && binOperator != nil && accumulator1 != nil {
            operation = operations[binOperator!]
        }
        else{
            operation = operations[mathSymbo]
        }
        switch operation! {
        case .constant(let val):
            accumulator = val
        case .unaryOperation(let function):
            if accumulator != nil {
                accumulator = function(accumulator!)
            }
            
        case .binaryOperation(let function):
            if accumulator != nil{
                accumulator = function(accumulator1!, accumulator!)
                binOperator = nil //reset
                accumulator1 = nil //reset
            }
        }
        
    }
    
    
    
    var result : Double? {
        get{
            return accumulator
        }
    }
    
}
