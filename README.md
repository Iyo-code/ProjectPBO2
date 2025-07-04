# API Pemesanan Vila Sederhana
Mata Kuliah: Pemrograman Berorientasi Objek  
Dosen: Wayan Oger Vihikan, S.T.I, M.I.T.  
Semester: 2  
Tahun: 2025

## Deskripsi Proyek
API Pemesanan Vila Sederhana adalah aplikasi RESTful API yang dibangun menggunakan bahasa pemrograman Java tanpa framework eksternal seperti Spring, dan memanfaatkan SQLite sebagai basis datanya. Proyek ini dirancang untuk memenuhi kebutuhan sistem reservasi vila secara digital dengan fitur-fitur inti seperti manajemen vila, tipe kamar, customer, booking, voucher, dan review.
Aplikasi ini memungkinkan pengguna untuk:
- Menambahkan, melihat, memperbarui, dan menghapus data vila beserta tipe kamarnya.
- Mencari vila yang tersedia berdasarkan tanggal check-in dan check-out.
- Menambahkan customer dan mengelola informasi mereka.
- Membuat pemesanan kamar oleh customer, dengan opsi menggunakan voucher diskon.
- Melakukan proses check-in dan check-out atas pemesanan.
- Memberikan review oleh customer terhadap pengalaman menginap mereka.

API ini dibangun dengan memanfaatkan Java HttpServer bawaan (tanpa framework web tambahan), dilengkapi dengan validasi input, error handling, dan dukungan header autentikasi melalui API Key.<br>
Proyek ini dikembangkan sebagai tugas akhir mata kuliah Pemrograman Berorientasi Objek, dengan pendekatan modular (berbasis package), prinsip pemisahan concern (model, service, repository, util), dan pendekatan CRUD berbasis URL path dan method HTTP standar (GET, POST, PUT, DELETE).

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

Port & API Key
- Server berjalan di http://localhost:8080
- Autentikasi : X-API-Key: villa-booking-api-key-2024

## Database
- villas
- room_types
- customers
- bookings
- reviews
- vouchers

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

## HTTP status
- [200 OK] Permintaan berhasil.
- [201 Created] Resource berhasil dibuat.
- [400 Bad Request] Permintaan tidak valid biasanya karena input JSON salah atau data tidak lengkap.
- [401 Unauthorized] API key tidak disertakan atau tidak valid.
- [404 Not Found] Resource tidak ditemukan.
- [405 Method Not Allowed] Endpoint valid, tapi method-nya tidak sesuai.
- [500 Internal Server Error] Terjadi error tak terduga di sisi server.

## Test Menggunakan Postman

### Villa

#### 1. Daftar semua vila
Request:
- Method: GET
- URL: http://localhost:8080/villas
- Headers: X-API-Key: villa-booking-api-key-2024

Dokumentasi:
![Daftar semua vila](https://github.com/user-attachments/assets/c8032ce0-17c7-4b53-9088-fcbd4703bd29)
Gambar tersebut menampilkan hasil permintaan data menggunakan metode GET pada aplikasi Postman yang mengakses endpoint http://localhost:8080/villas, di mana ditampilkan respon berupa daftar villa dalam format JSON lengkap dengan id, nama, deskripsi, dan alamat, yang menunjukkan bahwa API berjalan dengan baik dan berhasil mengembalikan data dari server lokal.

#### 2. Informasi detail suatu vila pada vila yang memiliki id 2
Request:
- Method: GET
- URL: http://localhost:8080/villas/2
- Headers: X-API-Key: villa-booking-api-key-2024

Dokumentasi:
![Informasi detail suatu vila pada vila yang memiliki id 2](https://github.com/user-attachments/assets/22e111d4-0f8f-4964-8364-a51422ff26c6)
Gambar tersebut menunjukkan bahwa pengguna menggunakan aplikasi Postman untuk mengakses endpoint http://localhost:8080/villas/2 dengan metode GET, yang menghasilkan respon data detail dari villa bernama "Villa Teratai", berupa objek JSON yang memuat informasi id, nama, deskripsi villa modern dengan kolam renang pribadi, serta alamat di Jl. Tirta No.2, yang menandakan bahwa sistem API berhasil mengembalikan data spesifik berdasarkan ID yang diminta.

#### 3. Informasi kamar suatu vila yang memiliki id 1, lengkap dengan fasilitas dan harga
Request:
- Method: GET
- URL: http://localhost:8080/villas/1/rooms
- Headers: X-API-Key: villa-booking-api-key-2024

Dokumentasi:
![Informasi kamar suatu vila yang memiliki id 1, lengkap dengan fasilitas dan harga](https://github.com/user-attachments/assets/ad1cb687-1bf9-42bc-8bca-117bed5cf840)
Gambar tersebut menunjukkan hasil request GET ke endpoint http://localhost:8080/villas/1/rooms menggunakan Postman, yang menampilkan detail kamar tipe Deluxe pada villa dengan ID 1, lengkap dengan kapasitas, harga, ukuran tempat tidur, serta fasilitas seperti AC, WiFi, TV, dan shower, namun tanpa kulkas.

#### 4. Daftar semua booking pada suatu vila yang memiliki id 1
Request:
- Method: GET
- URL: http://localhost:8080/villas/1/bookings
- Headers: X-API-Key: villa-booking-api-key-2024

Dokumentasi:
![Daftar semua booking pada suatu vila yang memiliki id 1](https://github.com/user-attachments/assets/9a28c6f6-027f-4414-bc48-8994ba070e37)
Gambar ini memperlihatkan respon dari server berupa data pemesanan kamar villa melalui endpoint tertentu, yang mencakup detail seperti identitas pelanggan, waktu menginap, total pembayaran setelah potongan voucher, serta konfirmasi bahwa transaksi berhasil dan tamu telah menyelesaikan proses check-in maupun check-out.

#### 5. Daftar semua review pada suatu vila yang memiliki id 2
Request:
- Method: GET
- URL: http://localhost:8080/villas/2/reviews
- Headers: X-API-Key: villa-booking-api-key-2024

Dokumentasi:
![Daftar semua review pada suatu vila yang memiliki id 2](https://github.com/user-attachments/assets/26defe1a-5da3-4698-a065-f7742f3eb5b2)
Gambar ini menunjukkan respon dari server terhadap permintaan GET ke endpoint http://localhost:8080/villas/2/reviews, yang berisi ulasan dari pengguna terhadap villa dengan ID 2. Data yang ditampilkan mencakup ID pemesanan terkait (booking: 2), rating bintang sebesar 4, judul ulasan "Nyaman dan tenang", serta isi ulasan yang menyatakan bahwa fasilitas dan suasana villa sangat baik, meskipun ada sedikit kekurangan pada area parkir.

#### 6. Pencarian ketersediaan vila berdasarkan tanggal check-in dan checkout
Request:
- Method: GET
- URL: http://localhost:8080/villas?ci_date=2025-07-05&co_date=2025-07-12
- Headers: X-API-Key: villa-booking-api-key-2024

Dokumentasi:
![Pencarian ketersediaan vila berdasarkan tanggal check-in dan checkout](https://github.com/user-attachments/assets/b2a62eea-66ae-444a-b15c-f4eee8647092)
Gambar ini menunjukkan respon dari server terhadap permintaan GET ke endpoint http://localhost:8080/villas dengan parameter tanggal check-in dan check-out, yang menampilkan daftar villa yang masih tersedia pada rentang waktu 5 hingga 7 Juli 2025. Data yang ditampilkan berupa informasi villa seperti ID, nama, deskripsi singkat, dan alamat masing-masing, menandakan bahwa sistem berhasil memfilter villa berdasarkan ketersediaan tanggal.

#### 7. Menambahkan data vila
Request:
- Method: POST
- URL: http://localhost:8080/villas
- Headers: X-API-Key: villa-booking-api-key-2024
- Body: json<br>
{<br>
  "name": "Villa Kenari",<br>
  "description": "Villa sejuk dengan pemandangan gunung.",<br>
  "address": "Jl. Merapi No.1"<br>
}

Dokumentasi:
![Menambah data vila](https://github.com/user-attachments/assets/df524389-1e05-4f53-b30d-0ba0c553f795)
Gambar tersebut menunjukkan bahwa pengguna mengirim data villa baru ke server menggunakan metode POST ke endpoint http://localhost:8080/villas melalui Postman. Data yang dikirim berisi nama, deskripsi, dan alamat villa. Server merespons dengan status 201 Created dan pesan "Villa created successfully", yang menandakan bahwa data berhasil ditambahkan.

#### 8. Menambahkan tipe kamar pada vila yang memiliki id 1
Request:
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

Dokumentasi:
![Menambahkan tipe kamar pada vila yang memiliki id 1](https://github.com/user-attachments/assets/c522a069-b5f4-4ece-8da8-c31aa0c868e0)
Gambar tersebut menunjukkan bahwa pengguna berhasil menambahkan kamar baru bertipe Deluxe ke villa dengan ID 1 melalui metode POST ke endpoint http://localhost:8080/villas/1/rooms. Data kamar mencakup kapasitas, harga, ukuran tempat tidur, dan berbagai fasilitas. Server merespons dengan status 201 Created dan pesan "Room created", yang menandakan data berhasil disimpan.

#### 9. Mengubah data suatu vila yang memiliki id 5
Request:
- Method: PUT
- URL: http://localhost:8080/villas/5
- Headers: X-API-Key: villa-booking-api-key-2024
- Body: json<br>
{<br>
  "name": "Villa Mawar Indah",<br>
  "description": "Villa dengan taman bunga berwarna-warni dan udara segar.",<br>
  "address": "Jl. Mawar No.5, Bedugul"<br>
}

Dokumentasi:
![Mengubah data suatu vila yang memiliki id 5](https://github.com/user-attachments/assets/fd8d06df-13e5-4f3e-8006-bd27b0927e12)
Gambar menunjukkan proses pembaruan data villa dengan ID 5 menggunakan metode PUT ke endpoint http://localhost:8080/villas/5. Data yang diperbarui meliputi nama, deskripsi, dan alamat villa. Server merespons dengan status 200 OK dan pesan "Villa updated successfully", yang berarti data berhasil diperbarui.

#### 10. Mengubah informasi kamar yang memiliki id 1 pada vila yang memiliki id 5
Request:
- Method: PUT
- URL: http://localhost:8080/villas/5/rooms/1
- Headers: X-API-Key: villa-booking-api-key-2024
- Body: json<br>
{<br>
"id": 1,<br>
"villa": 5,<br>
"name": "Super Deluxe Room",<br>
"quantity": 3,<br>
"capacity": 4,<br>
"price": 1200000,<br>
"bedSize": "King",<br>
"hasDesk": true,<br>
"hasAc": true,<br>
"hasTv": true,<br>
"hasWifi": true,<br>
"hasShower": true,<br>
"hasHotwater": true,<br>
"hasFridge": true<br>
}

Dokumentasi:
![Mengubah informasi kamar yang memiliki id 1 pada vila yang memiliki id 5](https://github.com/user-attachments/assets/e455dea9-cc07-4b47-b6e7-357074db1261)
Gambar menunjukkan proses pembaruan data kamar dengan ID 1 milik villa ID 5 melalui metode PUT ke endpoint http://localhost:8080/villas/5/rooms/1. Data kamar diperbarui, termasuk ukuran tempat tidur dan fasilitas seperti AC, TV, WiFi, shower, air panas, dan kulkas. Server merespons dengan status 200 OK dan pesan "Room updated", yang berarti perubahan berhasil disimpan.

#### 11. Menghapus kamar yang memiliki id 1 pada vila yang memiliki id 5
Request:
- Method: DELETE
- URL: http://localhost:8080/villas/5/rooms/1
- Headers: X-API-Key: villa-booking-api-key-2024

Dokumentasi:
![Menghapus kamar yang memiliki id 1 pada vila yang memiliki id 5](https://github.com/user-attachments/assets/6be0eace-5bbb-40bc-9af6-bb77990a0def)
Gambar menunjukkan bahwa pengguna menghapus kamar dengan ID 1 dari villa ID 5 menggunakan metode DELETE ke endpoint http://localhost:8080/villas/5/rooms/1. Server merespons dengan status 200 OK dan pesan "Room deleted", yang berarti kamar berhasil dihapus dari sistem.

#### 12. Menghapus suatu vila yang memiliki id 5
Request:
- Method: DELETE
- URL: http://localhost:8080/villas/5
- Headers: X-API-Key: villa-booking-api-key-2024

Dokumentasi:
![Menghapus suatu vila yang memiliki id 5](https://github.com/user-attachments/assets/61ab5da7-f977-4157-b140-5a22f656c5af)
Gambar menunjukkan proses penghapusan data villa dengan ID 5 menggunakan metode DELETE ke endpoint http://localhost:8080/villas/5. Server memberikan respon 200 OK dengan pesan "Villa deleted successfully", yang berarti villa berhasil dihapus dari sistem.

### Customer

#### 1. Daftar semua customer
Request:
- Method: GET
- URL: http://localhost:8080/customers
- Headers: X-API-Key: villa-booking-api-key-2024

Dokumentasi:
![Daftar semua customer](https://github.com/user-attachments/assets/f7ec20ea-0b43-4ad5-a9b9-68be844a96b5)
Gambar menunjukkan permintaan GET ke endpoint http://localhost:8080/customers untuk mengambil data seluruh pelanggan. Respons berhasil dengan status 200 OK, dan data yang ditampilkan mencakup beberapa pelanggan, masing-masing berisi informasi id, nama, email, dan nomor telepon. Terdapat data duplikat untuk pelanggan bernama Citra Dewi.

#### 2. Informasi detail seorang customer yang memiliki id 1
Request:
- Method: GET
- URL: http://localhost:8080/customers/1
- Headers: X-API-Key: villa-booking-api-key-2024

Dokumentasi:
![Informasi detail seorang customer yang memiliki id 1](https://github.com/user-attachments/assets/28927913-801b-4397-a553-a2759f2c8fbd)
Gambar menunjukkan permintaan GET ke endpoint http://localhost:8080/customers/1 untuk mengambil data pelanggan dengan ID 1. Server merespons dengan status 200 OK dan menampilkan informasi pelanggan bernama Ayu Wulandari beserta email dan nomor telepon.

#### 3. Daftar booking yang telah dilakukan oleh seorang customer yang memiliki id 2
Request:
- Method: GET
- URL: http://localhost:8080/customers/2/bookings
- Headers: X-API-Key: villa-booking-api-key-2024

Dokumentasi:
![Daftar booking yang telah dilakukan oleh seorang customer yang memiliki id 2](https://github.com/user-attachments/assets/5ab1d733-ba96-4c1d-afc8-e104e92eb794)
Permintaan GET menampilkan data booking pelanggan ID 2.
Pemesanan kamar ID 2, check-in 10 Juli 2025, check-out 12 Juli 2025, harga Rp1.600.000, pembayaran sukses, sudah check-in, belum check-out, tanpa voucher.

#### 4. Daftar ulasan yang telah diberikan oleh customer yang memiliki id 3
Request:
- Method: GET
- URL: http://localhost:8080/customers/3/reviews
- Headers: X-API-Key: villa-booking-api-key-2024

Dokumentasi:
![Daftar ulasan yang telah diberikan oleh customer yang memiliki id 3](https://github.com/user-attachments/assets/9778663b-669a-46d5-b1f8-78a6e1dd18eb)
Permintaan GET menampilkan data ulasan pelanggan ID 3.
Booking ID 3, beri rating 3 bintang, judul "Biasa saja", isi ulasan: lokasi strategis, tapi AC kurang dingin dan kamar agak gelap.

#### 5. Menambahkan customer baru (registrasi customer)
Request:
- Method: POST
- URL: http://localhost:8080/customers
- Headers: X-API-Key: villa-booking-api-key-2024
- Body: json<br>
{<br>
  "name": "Ayu Wulandari",<br>
  "email": "ayu@mail.com",<br>
  "phone": "0811111111"<br>
}

Dokumentasi:
![Menambahkan customer baru (registrasi customer)](https://github.com/user-attachments/assets/596d93d7-7b2c-4ee5-9c29-a721f764a641)
Permintaan POST menambahkan data pelanggan baru. Nama "Ayu Wulandari", email "ayu@mail.com", nomor telepon "0811111111". Server merespons dengan status 201 Created dan pesan "Customer created successfully".

#### 6. Customer melakukan pemesanan vila
Request:
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

Dokumentasi:
![Customer melakukan pemesanan vila](https://github.com/user-attachments/assets/760bcab4-6526-4bf9-aadc-ddbae1217043)
Permintaan POST menambahkan data pemesanan untuk customer ID 1. Tipe kamar ID 1, check-in pada 5 Juli 2025 pukul 14:00 dan check-out pada 7 Juli 2025 pukul 12:00. Harga awal Rp1.400.000, menggunakan voucher ID 1 sehingga harga akhir menjadi Rp1.120.000. Status pembayaran "success", pelanggan sudah check-in dan check-out. Server merespons dengan status 201 Created dan pesan "Booking created".

#### 7. Customer memberikan ulasan pada vila (berdasarkan informasi booking)
Request:
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

Dokumentasi:
![Customer memberikan ulasan pada vila (berdasarkan informasi booking)](https://github.com/user-attachments/assets/6f282cb5-0e71-43c2-8f55-da83d720bd0f)
Gambar menunjukkan proses pembuatan ulasan oleh customer dengan ID 1 untuk booking ID 1 menggunakan metode POST ke endpoint `http://localhost:8080/customers/1/bookings/1/reviews`. Data yang dikirim berupa JSON yang berisi ID booking, rating bintang (5), judul ulasan “Sangat Memuaskan!”, dan isi ulasan yang menggambarkan pengalaman positif selama menginap. Setelah permintaan dikirim, server merespons dengan status **201 Created** dan pesan **"Review created"**, yang menandakan bahwa ulasan berhasil dibuat dan disimpan di sistem.


#### 8. Mengubah seorang customer yang memiliki id 3
Request:
- Method: PUT
- URL: http://localhost:8080/customers/3
- Headers: X-API-Key: villa-booking-api-key-2024
- Body: json<br>
{<br>
  "name": "Citra Dewi Lestari",<br>
  "email": "citra.lestari@example.com",<br>
  "phone": "081234567899"
}

Dokumentasi:
![Mengubah seorang customer yang memiliki id 3](https://github.com/user-attachments/assets/d99ccc88-669e-4543-93f6-e83b3b0b0cb4)
Gambar menunjukkan proses pembaruan data customer dengan ID 3 menggunakan metode **PUT** ke endpoint `http://localhost:8080/customers/3`. Data yang diperbarui meliputi nama (`"Citra Dewi Lestari"`), email (`"citra.lestari@example.com"`), dan nomor telepon (`"081234567899"`). Permintaan dikirim dalam format JSON dan server memberikan respons dengan status **200 OK** serta pesan **"Customer updated successfully"**, yang menunjukkan bahwa data customer berhasil diperbarui di sistem.


#### 9. Menghapus seorang customer yang memiliki id 4
Request:
- Method: DELETE
- URL: http://localhost:8080/customers/4
- Headers: X-API-Key: villa-booking-api-key-2024

Dokumentasi:
![Menghapus seorang customer yang memiliki id 4](https://github.com/user-attachments/assets/39c406cc-275e-418b-bcc4-64687928965b)
Gambar menunjukkan proses penghapusan data customer dengan ID 4 menggunakan metode **DELETE** ke endpoint `http://localhost:8080/customers/4`. Permintaan ini tidak memerlukan body karena hanya bertujuan untuk menghapus data. Setelah permintaan dikirim, server merespons dengan status **200 OK** dan pesan **"Customer deleted successfully"**, yang menandakan bahwa data customer berhasil dihapus dari sistem.



### Voucher

#### 1. Daftar semua vouchers
Request:
- Method: GET
- URL: http://localhost:8080/vouchers
- Headers: X-API-Key: villa-booking-api-key-2024

Dokumentasi:
![Daftar semua vouchers](https://github.com/user-attachments/assets/94699da5-cbca-4f52-a715-c1ec61f1b971)
Gambar menunjukkan proses pengambilan data voucher menggunakan metode **GET** ke endpoint `http://localhost:8080/vouchers`. Permintaan ini tidak membutuhkan parameter tambahan, dan hasil respons berupa daftar voucher dalam format JSON. Terdapat dua voucher yang ditampilkan: voucher pertama memiliki kode `"PROMO20"` dengan deskripsi diskon 20% untuk awal bulan, berlaku dari 1 Juli 2025 hingga 31 Juli 2025; sedangkan voucher kedua memiliki kode `"PROMO15"` dengan deskripsi diskon 15% untuk pengguna baru, berlaku dari 1 Juli 2025 hingga 1 Januari 2026. Server merespons dengan status **200 OK**, waktu respons cepat yaitu 11 ms, dan ukuran data sebesar 567 B, yang menunjukkan bahwa data berhasil diambil dengan efisien.


#### 2. Informasi detail suatu voucher yang memiliki id 2
Request:

- Method: GET
- URL: http://localhost:8080/vouchers/2
- Headers: X-API-Key: villa-booking-api-key-2024

Dokumentasi:
![Informasi detail suatu voucher yang memiliki id 2](https://github.com/user-attachments/assets/8240849e-670f-4455-8f4d-8e0f7e8dab51)
Gambar menunjukkan proses pengambilan detail data voucher dengan ID 2 menggunakan metode **GET** ke endpoint `http://localhost:8080/vouchers/2`. Respons yang diterima berupa data voucher dalam format JSON yang mencakup kode voucher `"PROMO15"`, deskripsi `"Diskon 15% untuk pengguna baru"`, nilai diskon sebesar 15%, serta periode berlaku dari tanggal **1 Juli 2025 pukul 00:00:00** hingga **1 Januari 2026 pukul 23:59:59**. Server merespons dengan status **200 OK**, waktu respons sangat cepat yaitu **10 ms**, dan ukuran data sebesar **413 B**, yang menandakan bahwa detail voucher berhasil diambil dengan sukses dan efisien.


#### 3. Membuat voucher baru
Request:
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

Dokumentasi:
![Membuat voucher baru](https://github.com/user-attachments/assets/c007042f-f962-4842-8220-dbe9d7b4499c)
Gambar menunjukkan proses pembuatan data voucher baru menggunakan metode **POST** ke endpoint `http://localhost:8080/vouchers`. Data dikirim dalam format JSON yang berisi kode voucher `"PROMO20"`, deskripsi `"Diskon 20% untuk awal bulan"`, besaran diskon sebesar 20%, serta periode aktif mulai dari **1 Juli 2025 pukul 00:00:00** hingga **31 Juli 2025 pukul 23:59:59**. Setelah permintaan dikirim, server memberikan respons dengan status **201 Created** dan pesan **"Voucher created successfully"**, yang menandakan bahwa data voucher berhasil ditambahkan ke dalam sistem.


#### 4. Mengubah data suatu voucher yang memiliki id 2
Request:
- Method: PUT
- URL: http://localhost:8080/vouchers/2
- Headers: X-API-Key: villa-booking-api-key-2024
- Body: json<br>
{<br>
   "code": "PROMO15",<br>
    "description": "Diskon 15% untuk pengguna baru",<br>
    "discount": 15.0,<br>
    "startDate": "2025-07-01 00:00:00",<br>
    "endDate": "2026-12-31 23:59:59"<br>
}

Dokumentasi:
![Mengubah data suatu voucher yang memiliki id 2](https://github.com/user-attachments/assets/2fcbfabd-e215-49a8-968d-3934c7d6e017)
Gambar menunjukkan proses pembaruan data voucher dengan ID 2 menggunakan metode **PUT** ke endpoint `http://localhost:8080/vouchers/2`. Data yang diperbarui dalam format JSON mencakup kode voucher `"PROMO15"`, deskripsi `"Diskon 15% untuk pengguna baru"`, nilai diskon sebesar 15%, serta periode aktif dari **1 Juli 2025 pukul 00:00:00** hingga **31 Desember 2026 pukul 23:59:59**. Setelah permintaan dikirim, server merespons dengan status **200 OK** dan pesan **"Voucher updated successfully"**, yang menandakan bahwa informasi voucher berhasil diperbarui dalam sistem.


#### 5. Menghapus suatu voucher yang memiliki id 2
Request:
- Method: DELETE
- URL: http://localhost:8080/vouchers/2
- Headers: X-API-Key: villa-booking-api-key-2024

Dokumentasi:
![Menghapus suatu voucher yang memiliki id 2](https://github.com/user-attachments/assets/767935a6-937f-437f-b1cd-55bfd4a2af08)
Gambar menunjukkan proses penghapusan voucher dengan ID 2 menggunakan metode **DELETE** ke endpoint `http://localhost:8080/vouchers/2`. Permintaan ini tidak membutuhkan body karena hanya bertujuan untuk menghapus data voucher dari sistem. Setelah permintaan dikirim, server merespons dengan status **200 OK** dan pesan **"Voucher deleted successfully"**, yang menandakan bahwa data voucher telah berhasil dihapus dari database.

