//
//  ViewController.swift
//  Sosimple
//
//  Created by Fang Cao on 7/15/18.
//  Copyright © 2018 Entrepidea. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    
    @IBOutlet weak var display: UITextField!//we use UITextField! rather than UITextField? even display is an UITextField optional, this is called implicit optional unwrap. The benefit is to save us the trouble of typing ! each time when using display, otherwise we would have do this: display! to unwrap it everytime we use it.
    private var typingInTheMiddle = false

    @IBAction func digitTouched(_ sender: UIButton) {
        let digit = sender.currentTitle!
        if !typingInTheMiddle {
            display.text = digit
            typingInTheMiddle = true
        }
        else{
            
            let disText = display.text!
            if disText != "0" {
                display.text = disText + digit //be mindful that even if display.text is an optional, swift is smart to know this statement is to assign the string value to text's associated String type
            }
            else{
                display.text = digit
            }
            
        }
        
        
    }
    
    var displayValue : Double? { //computed property
        get{
            return Double(display.text!)
        }
        set{
            display.text = String(newValue!)
        }
    }
    
    private var brain : CalculatorBrain = CalculatorBrain()
    
    
    @IBAction func performOperation(_ sender: UIButton) {
        typingInTheMiddle = false
        if let operand = displayValue {
            brain.setOperand(operand)
        }
        
        brain.performOperation(sender.currentTitle!)
        displayValue = brain.result!
    }
    
}

