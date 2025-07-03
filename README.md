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

## Test Menggunakan Postman

### Villa

#### Menambahkan data vila
*Request:*
- Method: POST
- URL: http://localhost:8080/villas
- Headers: X-API-Key: villa-booking-api-key-2024
- Body: json<br>
{<br>
  "name": "Villa Kenari",<br>
  "description": "Villa sejuk dengan pemandangan gunung.",<br>
  "address": "Jl. Merapi No.1"<br>
}

*Dokumentasi:*
![Menambah data vila](https://github.com/user-attachments/assets/df524389-1e05-4f53-b30d-0ba0c553f795)

#### Menambahkan tipe kamar pada vila
*Request:*
- Method: POST
- URL: http://localhost:8080/villas/1/rooms
- Headers: X-API-Key: villa-booking-api-key-2024
- Body: json<br>
{<br>
  "name": "Deluxe",<br>
  "quantity": 1,<br>
  "capacity": 2,<br>
  "price": 700000,<br>
  "bedSize": "Queen",<br>
  "hasDesk": true,<br>
  "hasAc": true,<br>
  "hasTv": true,<br>
  "hasWifi": true,<br>
  "hasShower": true,<br>
  "hasHotwater": true,<br>
  "hasFridge": false<br>
}

*Dokumentasi:*
![Menambahkan tipe kamar pada vila](https://github.com/user-attachments/assets/c522a069-b5f4-4ece-8da8-c31aa0c868e0)

### Customer

#### Menambahkan customer baru (registrasi customer)
*Request:*
- Method: POST
- URL: http://localhost:8080/customers
- Headers: X-API-Key: villa-booking-api-key-2024
- Body: json<br>
{<br>
  "name": "Ayu Wulandari",<br>
  "email": "ayu@mail.com",<br>
  "phone": "0811111111"<br>
}

*Dokumentasi:*
![Menambahkan customer baru (registrasi customer)](https://github.com/user-attachments/assets/596d93d7-7b2c-4ee5-9c29-a721f764a641)

#### Customer melakukan pemesanan vila
*Request:*
- Method: POST
- URL: http://localhost:8080/customers/1/bookings
- Headers: X-API-Key: villa-booking-api-key-2024
- Body: json<br>
{<br>
"customer": 1,<br>
"roomType": 1,<br>
"checkinDate": "2025-07-05 14:00:00",<br>
"checkoutDate": "2025-07-07 12:00:00",<br>
"price": 1400000,<br>
"voucher": 1,<br>
"finalPrice": 1120000,<br>
"paymentStatus": "success",<br>
"hasCheckedIn": true,<br>
"hasCheckedOut": true<br>
}

*Dokumentasi:*
![Customer melakukan pemesanan vila](https://github.com/user-attachments/assets/760bcab4-6526-4bf9-aadc-ddbae1217043)

#### Customer memberikan ulasan pada vila (berdasarkan informasi booking)
*Request:*
- Method: POST
- URL: http://localhost:8080/customers/1/bookings/1/reviews
- Headers: X-API-Key: villa-booking-api-key-2024
- Body: json<br>
{<br>
  "booking": 1,<br>
  "star": 5,<br>
  "title": "Sangat Memuaskan!",<br>
  "content": "Villa bersih, staf ramah, dan pemandangan luar biasa. Akan kembali lagi!"<br>
}

*Dokumentasi:*
![Customer memberikan ulasan pada vila (berdasarkan informasi booking)](https://github.com/user-attachments/assets/6f282cb5-0e71-43c2-8f55-da83d720bd0f)

### Voucher

#### Membuat voucher baru
*Request:*
- Method: POST
- URL: http://localhost:8080/vouchers
- Headers: X-API-Key: villa-booking-api-key-2024
- Body: json<br>
{<br>
  "code": "PROMO20",<br>
  "description": "Diskon 20% untuk awal bulan",<br>
  "discount": 20,<br>
  "startDate": "2025-07-01 00:00:00",<br>
  "endDate": "2025-07-31 23:59:59"<br>
}

*Dokumentasi:*
![Membuat voucher baru](https://github.com/user-attachments/assets/c007042f-f962-4842-8220-dbe9d7b4499c)
