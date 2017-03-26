package org.usfirst.frc.team1492.robot.autonomous;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MissionScript {
    
    public static Mission parseMission(int id, String code, CommandFactory factory) {
        Mission mission = new Mission(id);
        String[] lines = code.split("\n");
        for (String line : lines) {
            String lineWithoutComment = line.split("//")[0];

            String[] parenSplit = lineWithoutComment.split("\\(");
            if (parenSplit.length == 2) {
                String name = parenSplit[0].trim();
                String signature = name + "(";
                String[] parametersTogether = parenSplit[1].split("\\)");
                
                Class<?>[] parameterClasses;
                Object[] parameterValues;
                
                if(parametersTogether.length > 0){
                    String[] parameters = parametersTogether[0].split(",");
                    int paramIndex = 0;
                    parameterClasses = new Class[parameters.length];
                    parameterValues = new Object[parameters.length];
                    for (String parameter : parameters) {
                        if (parameter.equals("true")) {
                            parameterClasses[paramIndex] = boolean.class;
                            parameterValues[paramIndex] = true;
                        } else if (parameter.equals("false")) {
                            parameterClasses[paramIndex] = boolean.class;
                            parameterValues[paramIndex] = false;
                        } else {
                            try {
                                parameterClasses[paramIndex] = double.class;
                                parameterValues[paramIndex] = Double.parseDouble(parameter);
                            } catch (NumberFormatException e) {
                                System.err.println("Failed to parse parameter as boolean or double: \""
                                        + parameter + "\"");
                                return null;
                            }
                        }
                        paramIndex++;
                    }
                    for (int p = 0; p < parameterClasses.length; p++) {
                        signature += parameterClasses[p].getName();
                        if (p != parameterClasses.length - 1) {
                            signature += ", ";
                        }
                    }
                }else{
                    parameterClasses = new Class[0];
                    parameterValues = new Object[0];
                }
                signature += ")";
                try {
                    Method method = CommandFactory.class.getMethod(name, parameterClasses);
                    
                    Object returnValue = method.invoke(factory, parameterValues);
                    
                    if(returnValue == null){
                        System.err.println("Method returned null when called: " + lineWithoutComment);
                        return null;
                    }
                    
                    if(!(returnValue instanceof Command)){
                        System.err.println("Method does not return a Command: " + signature+" : "+method.getReturnType().getName());
                        return null;
                    }

                    mission.add((Command)returnValue);

                } catch (NoSuchMethodException e) {
                    System.err.println("Failed to find method: " + signature + "");
                    return null;
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Failed to find opening parenthesis in line: \""
                        + lineWithoutComment + "\"");
                return null;
            }
        }
        return mission;
    }

}
