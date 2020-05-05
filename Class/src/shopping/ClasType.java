/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shopping;

/**
 *
 * @author 69465
 */
public class ClasType { //分类对象数据结构
    Classification clas; //数据域
    Classification fatherClas; //父分类引用
    Classification botherClas[];
    Classification childClas[];
}
