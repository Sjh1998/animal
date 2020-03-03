package web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 86181 on 2020/2/25.
 */

public class Test {
    //  1）volatile本质是在告诉jvm当前变量在寄存器中的值是不确定的,需要从主存中读取,synchronized则是锁定当前变量,只有当前线程可以访问该变量,其他线程被阻塞住.
//2）volatile仅能使用在变量级别,synchronized则可以使用在变量,方法.
//3）volatile仅能实现变量的修改可见性,而synchronized则可以保证变量的修改可见性和原子性.
//　《Java编程思想》上说，定义long或double变量时，如果使用volatile关键字，就会获得（简单的赋值与返回操作）原子性。
//            　　
// 4）volatile不会造成线程的阻塞,而synchronized可能会造成线程的阻塞.
//
//5、当一个域的值依赖于它之前的值时，volatile就无法工作了，如n=n+1,n++等。如果某个域的值受到其他域的值的限制，那么volatile也无法工作，如Range类的lower和upper边界，必须遵循lower<=upper的限制。
//
// 6、使用volatile而不是synchronized的唯一安全的情况是类中只有一个可变的域。
    public static volatile Map<String, Integer> animal = new ConcurrentHashMap<String, Integer>();

    // 领取宠物接口
    public synchronized String pull(String type) {
        try {
            if (animal.get(type) == null || animal.get(type).equals(null)) {
                animal.put(type, 1);
            } else {
                int i = animal.get(type);
                animal.put(type, i + 1);
            }
            return "ok";
        } catch (Exception e) {
            return "false";
        }

    }

    //计算宠物受欢迎度
    public List<String> calculate() {
        try {
            System.out.println(">>>>" + animal);
            List<String> list = new ArrayList<String>();
            Map<String, Integer> map = new HashMap<String, Integer>();
            map = animal;
            System.out.println(">>>>2" + map);
            //循环加入集合
            for (String key : map.keySet()) {//keySet获取map集合key的集合  然后在遍历key即可
                String value = map.get(key).toString();//
                System.out.println("key:" + key + " vlaue:" + value);
                list.add(key + ":" + value);
            }
            //循环集合进行排序
            String[] strings = new String[list.size()];
            list.toArray(strings);
            System.out.println(">>>>>>" + strings);
            QuickSort(strings, 0, strings.length - 1);
            for (int i = 0; i < strings.length; i++) {
                System.out.print(strings[i] + " ");
            }
            list = java.util.Arrays.asList(strings);
            List arrList = new ArrayList(list);
            arrList.add("ok");
            return arrList;
        } catch (Exception e) {
            return null;
        }

    }

    //算法从小到大
    public static void quickSort(String[] arr, int low, int high) {
        String o;
        int i, j, temp;
        String t;
        if (low > high) {
            return;
        }
        i = low;
        j = high;
        //temp就是基准位
        temp = Integer.valueOf(arr[low].split(":")[1]);
        o = arr[low].split(":")[0];
        while (i < j) {
            //先看右边，依次往左递减
            while (temp <= Integer.valueOf(arr[j].split(":")[1]) && i < j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp >= Integer.valueOf(arr[i].split(":")[1]) && i < j) {
                i++;
            }
            //如果满足条件则交换
            if (i < j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t + "";

            }

        }
        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        System.out.println(">>>>>2>>>>>" + arr[i]);
        System.out.println(o + ">>>>>" + temp);
        arr[i] = o + ":" + temp;
        System.out.println(">>>>>3>>>>>" + arr[i]);
        //递归调用左半数组
        quickSort(arr, low, j - 1);
        //递归调用右半数组
        quickSort(arr, j + 1, high);
    }

    //从大到小
    public static void QuickSort(String[] a, int left, int right) {
        if (left >= right) {
            return;
        }
        String o;
        o = a[left].split(":")[0];
        int key = Integer.valueOf(a[left].split(":")[1]);
        int i = left;
        int j = right;
        while (i < j) {
//j向左移，直到遇到比key大的值
            while (Integer.valueOf(a[j].split(":")[1]) <= key && i < j) {
                j--;
            }
//i向右移，直到遇到比key小的值
            while (Integer.valueOf(a[i].split(":")[1]) >= key && i < j) {
                i++;
            }
            if (i < j) {//互换
                String temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }
        a[left] = a[i];
        a[i] = o + ":" + key;
        QuickSort(a, left, i - 1);
        QuickSort(a, i + 1, right);
    }

}
