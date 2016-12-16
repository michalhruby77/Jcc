package com.leoni.trying;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 18.1.2013
 * Time: 15:11
 * To change this template use File | Settings | File Templates.
 */
public class SimplyMath
    {
    private int constant;

    public SimplyMath()
        {
        constant = 0;
        }

    public SimplyMath(int c)
        {
        constant = c;
        }

    public int add (int num)
        {
        return num + constant;
        }

    public int subtract (int num)
        {
        return num - constant;
        }

    public int multiply (int num)
        {
        return num * constant;
        }

    public int divide (int num)
        {
        Random r = new Random();
        r.nextInt();
        return constant != 0 ? num / constant : 0;
        }

    public int getConstant()
        {
        return constant;
        }

    public void setConstant(int constant)
        {
        this.constant = constant;
        }
    }
