package org.example

// 1. กำหนด data class สำหรับเก็บข้อมูลสินค้า
data class Product(val name: String, val price: Double, val category: String)
fun calculateTotalElectronicsPriceOver500(products: List<Product>): Double =
    products
        .filter { it.category == "Electronics" && it.price > 500.0 }
        .sumOf { it.price }

//Returns count of all Electronics items priced > 500

fun countElectronicsOver500(products: List<Product>): Int =
    products.count { it.category == "Electronics" && it.price > 500.0 }

fun main() {
    // 2. สร้างรายการสินค้าตัวอย่าง (List<Product>)
    val products = listOf(
        Product("Laptop",     35000.0,  "Electronics"),
        Product("Smartphone", 25000.0,  "Electronics"),
        Product("T-shirt",      450.0,  "Apparel"),
        Product("Monitor",     7500.0,  "Electronics"),
        Product("Keyboard",     499.0,  "Electronics"), // ราคาไม่เกิน 500
        Product("Jeans",       1200.0,  "Apparel"),
        Product("Headphones",  1800.0,  "Electronics")  // ตรงตามเงื่อนไข
    )

    println("รายการสินค้าทั้งหมด:")
    products.forEach { println(it) }
    println("--------------------------------------------------")

    // --- โจทย์: จงหาผลรวมราคาสินค้าทั้งหมดในหมวด 'Electronics' ที่มีราคามากกว่า 500 บาท ---

    // 3. วิธีที่ 1: การใช้ Chaining กับ List โดยตรง
    val totalElecPriceOver500 = products
        .filter  { it.category == "Electronics" }  // กรองหมวด Electronics
        .filter  { it.price    > 500.0           }  // กรองราคา > 500
        .map     { it.price                     }  // ดึงเฉพาะราคา
        .sum()                                     // หาผลรวม

    println("วิธีที่ 1: ใช้ Chaining กับ List")
    println("ผลรวมราคาสินค้า Electronics ที่ราคา > 500 บาท: $totalElecPriceOver500 บาท")
    println("--------------------------------------------------")



    // 4. (ขั้นสูง) วิธีที่ 2: การใช้ .asSequence() เพื่อเพิ่มประสิทธิภาพ
    val totalElecPriceOver500Sequence = products
        .asSequence()                              // แปลง List เป็น Sequence
        .filter  { it.category == "Electronics" }
        .filter  { it.price    > 500.0           }
        .map     { it.price                     }
        .sum() // Terminal operation เรียกใช้จริง

    println("วิธีที่ 2: ใช้ .asSequence() (ขั้นสูง)")
    println("ผลรวมราคาสินค้า Electronics ที่ราคา > 500 บาท: $totalElecPriceOver500Sequence บาท")

    // --- โจทย์: จงหาผลรวมราคาสินค้าทั้งหมดในหมวด 'Electronics' ที่มีราคามากกว่า 500 บาท ---
    println("--------------------------------------------------")

    // --- โจทย์ใหม่: แบ่งกลุ่มสินค้าตาม range ราคา ---
    println("การแบ่งกลุ่มสินค้าตาม range ราคา:")

    val groupedByPriceRange: Map<String, List<Product>> = products.groupBy { product ->
        when {
            product.price <=   1_000.0 -> "ราคาต่ำกว่าหรือเท่ากับ 1,000 บาท"
            product.price <  10_000.0 -> "ราคาระหว่าง 1,001 – 9,999 บาท"
            else                       -> "ราคา 10,000 บาทขึ้นไป"
        }
    }

    // พิมพ์ผลลัพธ์แต่ละกลุ่ม
    groupedByPriceRange.forEach { (label, list) ->
        println("$label:")
        list.forEach { p ->
            println("  - ${p.name}: ${"%,.2f".format(p.price)} บาท")
        }
        println()
    }
}