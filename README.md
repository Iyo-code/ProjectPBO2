# API Pemesanan Vila Sederhana

## Deskripsi Proyek
Sistem pemesanan villa sederhana berbasis REST API menggunakan Java HttpServer dan SQLite. Proyek ini dibuat sebagai tugas mata kuliah PBO II.

## Anggota Kelompok
| NIM | Nama |
|-----|------|
|2405551168|Trio Suro Wibowo|
|2405551155|I Made Agus Hendra Diwangga|
|2405551144|I Wayan Daffa Adhirajasa|
|2405551095|I Gusti Agung Dananjaya Putra|

## Cara Menjalankan Program
Persyaratan Sistem
- Java JDK 11 atau lebih tinggi
- SQLite
- Postman (untuk testing)
#
- Server berjalan di http://localhost:8080
- Autentikasi : X-API-Key: villa-booking-api-key-2024

## API Endpoints
| Entitas | Method | Endpoint | Deskripsi |
|---------|--------|----------|-----------|
| Villa | GET | /villas | Daftar semua vila |
|| GET | /villas/{id} | Informasi detail suatu vila |
|| GET | /villas/{id}/rooms | Informasi kamar suatu vila, lengkap dengan fasilitas dan harga |
|| GET | /villas/{id}/bookings | Daftar semua booking pada suatu vila |
|| GET | /villas/{id}/reviews | Daftar semua review pada suatu vila |
|| GET | /villas?ci_date={date}&co_date={date} | Pencarian ketersediaan vila berdasarkan tanggal check-in dan checkout |
|| POST | /villas | Menambahkan data vila |
|| POST | /villas/{id}/rooms | Menambahkan tipe kamar pada vila |
|| PUT | /villas/{id} | Mengubah data suatu vila |
|| PUT | /villas/{id}/rooms/{id} | Mengubah informasi kamar suatu vila |
|| DELETE | /villas/{id} | Menghapus data suatu vila |
|| DELETE | /villas/{id}/rooms/{id} | Menghapus kamar suatu vila |
|Customer| GET | /customers | Daftar semua customer |
|| GET | /customers/{id} | Informasi detail seorang customer |
|| GET | /customers/{id}/bookings | Daftar booking yang telah dilakukan oleh seorang customer |
|| GET | /customers/{id}/reviews | Daftar ulasan yang telah diberikan oleh customer |
|| POST | /customers | Menambahkan customer baru (registrasi customer) |
|| POST | /customers/{id}/bookings | Customer melakukan pemesanan vila |
|| POST | /customers/{id}/bookings/{id}/reviews | Customer memberikan ulasan pada vila (berdasarkan informasi booking) |
|| PUT | /customers/{id} | Mengubah data seorang customer |
|| DELETE | /customers/{id} | Menghapus data seorang customer |
|Voucher| GET | /vouchers | Daftar semua voucher |
|| GET | /vouchers/{id} | Informasi detail suatu voucher |
|| POST | /vouchers | Membuat voucher baru |
|| PUT | /vouchers/{id} | Mengubah data suatu voucher |
|| DELETE | /vouchers/{id} | Menghapus data suatu voucher |
