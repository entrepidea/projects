//
//  ViewController.swift
//  Calculator
//
//  Created by Fang Cao on 7/10/17.
//  Copyright © 2017 Entrepidea. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    
    @IBOutlet weak var display: UILabel!
    
    var userInMiddleofTyping = false
    
    private var brain = CalculatorBrain()
    
    //compute value
    var displayVale : Double{
        get{
            return Double(display.text!)!
        }
        set{
            display.text = String(newValue)
        }
    }
    
    @IBAction func touchDigit(_ sender: UIButton) {
        let digit: String = sender.currentTitle!
        let currentDisplayText = display.text!
        if userInMiddleofTyping {
            display.text = currentDisplayText + digit
        }
        else{
            display.text = digit
            userInMiddleofTyping = true
        }
        
        //check if the text is a sole '.', then the operand is finished setting yet.
        if display.text! == "."{
            display.text = String("0.")
            return
        }
        brain.setOperand(displayVale)
    }
    
    @IBAction func performOption(_ sender: UIButton) {
        userInMiddleofTyping = false
        if let symbo = sender.currentTitle
        {
            
            brain.performOperand(symbo)
            displayVale = brain.result!
        }
    }
    
    //this occurs when +,-,* or / button is clicked
    @IBAction func onBinaryOperator(_ sender: UIButton) {
        userInMiddleofTyping = false
        if let symbo = sender.currentTitle{
            brain.setBinaryOperator(symbo)
        }
        
    }
    
}

