# Reflection Module 2


1. Daftar masalah kualitas kode yang telah diperbaiki dan strategi penyelesaiannya:
Penggunaan dependency yang tidak diperlukan → Menghapus dependensi yang tidak digunakan untuk mengurangi ukuran build dan meningkatkan efisiensi.
Pengujian yang kurang cakupan (code coverage rendah) → Menambahkan lebih banyak unit test, terutama untuk controller dan service, menggunakan Mockito untuk memastikan semua fungsionalitas diuji.
Format kode yang tidak konsisten → Menggunakan Checkstyle dan SonarQube untuk memastikan standar penulisan kode terpenuhi dan memperbaiki issue seperti indentation yang salah atau naming yang tidak sesuai konvensi.


2. Evaluasi Implementasi CI/CD:
Implementasi CI/CD yang dibuat sudah memenuhi definisi Continuous Integration dan Continuous Deployment.
Continuous Integration (CI) sudah diterapkan karena setiap perubahan kode yang di-push ke repository otomatis memicu build, menjalankan test suite, dan melakukan analisis kode untuk memastikan tidak ada regresi atau error sebelum kode digabungkan ke branch utama.
Continuous Deployment (CD) juga telah diterapkan karena setelah pipeline berhasil, kode otomatis dideploy ke PaaS tanpa intervensi manual, memastikan bahwa aplikasi selalu dalam keadaan terbaru dan dapat langsung digunakan oleh pengguna.
Proses ini mendukung otomatisasi dan efisiensi, mengurangi kemungkinan human error, serta mempercepat waktu rilis fitur baru karena setiap perubahan kode bisa langsung diproduksi setelah melewati semua tahapan validasi di pipeline CI/CD.





# Reflection 1

Menurut saya, selama proses pengembangan fitur edit dan delete produk, saya mendapatkan beberapa kesulitan sehingga dapat dijadikan pelajaran dalam manajemen versi menggunakan Git serta implementasi CRUD dalam Spring Boot dengan Thymeleaf. Saya belajar bagaimana mengatur branch yang berbeda untuk setiap fitur dan melakukan merge ke branch utama dengan aman. Selain itu, saya juga memahami bagaimana menangani error terkait mapping endpoint serta mengoptimalkan pengelolaan data produk dengan UUID.

## Usage

```xhtml
<html>
```

Selama tahap implementasi, saya menggunakan template HTML berbasis Thymeleaf untuk menampilkan daftar produk dan menambahkan tombol edit serta delete. Dengan adanya fitur ini, pengguna dapat dengan mudah mengelola data produk yang tersedia.


# Reflection 2

## Refleksi tentang Pengujian Unit dan Kualitas Kode
Setelah menulis unit test untuk CreateProductFunctionalTest.java, saya merasa bahwa pengujian unit sangat penting untuk memastikan bahwa fungsionalitas aplikasi bekerja dengan benar. Pengujian ini memberikan keyakinan bahwa perubahan pada kode tidak akan menyebabkan bug atau regresi yang tidak terduga.

Berapa banyak unit test yang harus dibuat dalam sebuah kelas?
Jumlah unit test yang diperlukan dalam sebuah kelas bergantung pada kompleksitas fungsionalitas yang diuji. Sebaiknya kita menulis pengujian untuk setiap kasus penting, termasuk:

Kasus sukses (fungsi berjalan dengan baik).
Kasus kesalahan atau input tidak valid.
Kasus batas (misalnya, produk dengan jumlah nol atau sangat besar).
Bagaimana memastikan bahwa unit test kita cukup untuk memverifikasi program?
Untuk memastikan pengujian cukup, kita dapat menggunakan code coverage sebagai metrik. Code coverage mengukur seberapa banyak kode yang telah diuji oleh unit test. Namun, memiliki 100% code coverage tidak berarti kode kita bebas dari bug. Code coverage hanya menunjukkan bahwa semua bagian kode telah dieksekusi, tetapi tidak menjamin bahwa semua kemungkinan skenario telah diuji.

## Refleksi tentang Kualitas Kode dalam Functional Test Suite Baru
Jika saya diminta untuk membuat functional test suite baru untuk memverifikasi jumlah item dalam daftar produk, dan saya menggunakan setup serta variabel yang sama dengan CreateProductFunctionalTest.java, ada beberapa potensi masalah clean code yang bisa muncul:

Duplikasi Kode

Setiap functional test suite memiliki prosedur setup yang sama (@BeforeEach, baseUrl, driver.get(baseUrl), dll.).
Duplikasi ini menyebabkan kode lebih sulit dirawat dan diperbarui.
Pelanggaran Prinsip DRY (Don't Repeat Yourself)

Jika ada perubahan dalam pengaturan (misalnya, URL dasar berubah), kita harus memperbarui banyak file sekaligus.
Ini meningkatkan risiko inkonsistensi antara test suite yang berbeda.
Kurangnya Pemanfaatan Abstraksi dan Reusability

Bisa jadi ada banyak kode serupa yang bisa diekstraksi ke metode helper atau kelas utilitas untuk meningkatkan keterbacaan.

## Saran Perbaikan untuk Membuat Kode Lebih Bersih
Gunakan Kelas Base untuk Pengujian

Buat kelas dasar BaseFunctionalTest.java yang menangani setup umum, seperti baseUrl, konfigurasi WebDriver, dan metode helper lainnya.
Setiap functional test suite baru cukup mewarisi (extends) kelas ini agar tidak perlu menulis ulang setup.
Gunakan Parameterisasi dalam Pengujian

Gunakan parameterisasi dalam unit test (misalnya dengan @ParameterizedTest di JUnit 5) untuk mengurangi duplikasi pengujian yang hanya berbeda dalam input.
Gunakan Page Object Model (POM)

Pisahkan logika interaksi dengan halaman web ke dalam kelas-kelas yang merepresentasikan halaman atau komponen UI tertentu.
Ini akan membuat test suite lebih modular dan mudah diperbarui.
Dengan menerapkan prinsip-prinsip clean code di atas, functional test suite akan lebih bersih, lebih terorganisir, dan lebih mudah dipelihara.