//
//  CalculatorBrain.swift
//  Sosimple
//
//  Created by Fang Cao on 7/22/18.
//  Copyright © 2018 Entrepidea. All rights reserved.
//

import Foundation

func changeSign(_ operand : Double) -> Double {
    return -operand
}

struct CalculatorBrain {
    private var accumulator : Double?
    
    private enum Operation {
        case constant(Double)
        case unary((Double)->Double)
        case binary((Double,Double) ->Double)
        case equal
    }
    
    private var operations : Dictionary<String, Operation> = [
        "π"     : Operation.constant(Double.pi),
        "√"     : Operation.unary(sqrt),
        "Cos"   : Operation.unary(cos),
        "±"     : Operation.unary(changeSign),
        "×"      : Operation.binary({$0*$1}),
        "÷"      : Operation.binary({$0/$1}),
        "+"      : Operation.binary({$0+$1}),
        "-"      : Operation.binary({$0-$1}),
        "="      : Operation.equal
        
    ]
    
    
    mutating func performOperation(_ symbol : String){
        if let operation = operations[symbol]{
            switch operation {
            case .constant(let value):
                accumulator = value
            case .unary(let function):
                if accumulator != nil {
                    accumulator = function(accumulator!)
                }
            case .binary(let function):
                if let operand = accumulator {
                    pbo = PendingBinaryOperation (firstOperand: operand, function: function)
                }
                
            case .equal:
                if accumulator != nil {
                    accumulator = pbo?.perform(with: accumulator!)
                    pbo = nil
                }
            }
        }
    }
    private var pbo : PendingBinaryOperation?
    
    private struct PendingBinaryOperation {
        let firstOperand : Double
        let function: (Double, Double) -> Double
        func perform(with secondOperand : Double) -> Double {
            return function(firstOperand, secondOperand)
        }
    }
    
    mutating func setOperand(_ operand : Double){
        accumulator = operand
    }
    
    var result : Double?{
        get{
            return accumulator
        }
    }
    
}
