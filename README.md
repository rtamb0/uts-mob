# Ralf Fadilla - 0112523048
# TOPIK C — INVENTARIS BARANG PRIBADI

# BarangKu Application

Aplikasi Android berbasis **Jetpack Compose**, **Navigation Compose (Type-Safe Navigation)**, **Room 3 (Local Database)**, **Preferences DataStore**, **Architecture Components (ViewModel, StateFlow & Repository Pattern)**, dan **Material 3** untuk pencatatan dan pengelolaan inventaris barang (BarangKu).

Aplikasi ini menerapkan arsitektur Android modern (**Data Layer + Repository Layer + UI Layer**) dengan prinsip *Unidirectional Data Flow* (UDF), persistensi data lokal SQLite melalui Room 3, pengaturan preferensi tampilan via DataStore, serta pengelolaan state yang aman terhadap perubahan konfigurasi (*configuration changes*).

---

## 1. Tentang Aplikasi

**BarangKu** adalah aplikasi pencatatan barang dan inventaris pribadi yang dirancang untuk memudahkan pengguna dalam menyimpan data tempat penyimpanan barang, deskripsi/keterangan, serta status ketersediaan barang.

### Fitur Utama
* 📦 **Katalog Barang (Home Screen)**: Menampilkan daftar barang dalam pilihan tampilan **Grid (2 Kolom)** atau **Compact List (1 Kolom)** yang terurut berdasarkan ID terbaru. Status ketersediaan (`tersedia`) dapat diubah secara langsung dari kartu barang.
* ➕ **Tambah Barang (Add Screen)**: Pengguna dapat menambah barang baru dengan mengisi Nama Barang, Lokasi Simpan, Keterangan, dan status Ketersediaan.
* 🔍 **Detail Barang (Detail Screen)**: Menampilkan rincian informasi barang (Nama Barang, Lokasi Simpan, Keterangan) dengan opsi navigasi ke halaman pengeditan.
* ✏️ **Edit & Hapus Barang (Edit Screen)**: Memungkinkan pembaruan data barang serta penghapusan data dengan konfirmasi dialog (*AlertDialog*).
* ⚙️ **Pengaturan Tampilan (Preferences DataStore)**: Fitur *Compact View* yang opsinya dapat di-toggle pada `AppHeader` dan tersimpan secara persisten menggunakan Jetpack DataStore Preferences (`SettingsDataStore`).
* 🌱 **Database Auto-Seeding**: Aplikasi secara otomatis melakukan *initial data seeding* pada peluncuran pertama jika database Room masih kosong.
* 🧭 **Type-Safe Navigation**: Menggunakan `@Serializable` rute (`Home`, `Add`, `Detail(id: Int)`, `Edit(id: Int)`) untuk navigasi antar layar yang aman terhadap kesalahan tipe data.
* ⚡ **ViewModel & StateFlow Architecture**: Menggunakan `BarangViewModel`, `SettingsViewModel`, `AddViewModel`, dan `EditViewModel` dengan enkapsulasi `MutableStateFlow` / `StateFlow` serta konsumsi state yang *lifecycle-aware* (`collectAsStateWithLifecycle()`).
* 🛡️ **Form Validation**: Memvalidasi input form secara otomatis (`isFormValid`), sehingga tombol *Save* hanya aktif jika field wajib (Nama Barang & Lokasi Simpan) terisi.

---

## 2. Arsitektur & Struktur Kode

Struktur kode proyek terorganisasi ke dalam arsitektur berlapis di `app/src/main/java/com/example/myapplication`:

```
com.example.myapplication/
├── BarangApplication.kt         # Application Class (Singletons Database, Repository, & DataStore)
├── MainActivity.kt              # Main Activity & Entry Point
├── data/
│   ├── local/
│   │   ├── dao/
│   │   │   └── BarangDao.kt     # Data Access Object (Room 3 DAO interface)
│   │   ├── database/
│   │   │   └── AppDatabase.kt   # Room Database Singleton (AndroidSQLiteDriver)
│   │   ├── datastore/
│   │   │   └── SettingsDataStore.kt # DataStore Preferences (Compact View Preference)
│   │   ├── entity/
│   │   │   └── BarangEntity.kt  # Room Entity class (table: "barang")
│   │   └── source/
│   │       └── BarangData.kt    # Initial data seed (initialBarang)
│   └── repository/
│       └── BarangRepository.kt  # Repository Layer (Single Source of Truth)
├── navigation/
│   └── AppNavigation.kt        # NavHost setup, Type-Safe Routes, & ViewModel Injection
└── ui/
    ├── App.kt                   # Container utama aplikasi (AppHeader + AppNavigation)
    ├── add/                     # UI Layer: Add Feature
    │   ├── AddRoute.kt          # Stateful Route: Menghubungkan ViewModel & Callbacks
    │   ├── AddScreen.kt         # Stateless Screen UI
    │   ├── AddUiState.kt        # State form & logika validasi (isFormValid)
    │   └── AddViewModel.kt      # State Holder untuk form tambah barang
    ├── components/
    │   ├── AppHeader.kt         # Header aplikasi dengan toggle Compact View
    │   └── BarangCard.kt        # Card item barang
    ├── detail/
    │   └── DetailScreen.kt      # Layar Rincian/Detail Barang
    ├── edit/                    # UI Layer: Edit Feature
    │   ├── EditRoute.kt         # Stateful Route
    │   ├── EditScreen.kt        # Stateless Screen UI (dengan dialog konfirmasi hapus)
    │   ├── EditUiState.kt       # State form edit & logika validasi (isFormValid)
    │   └── EditViewModel.kt     # State Holder untuk form edit barang
    ├── home/
    │   └── HomeScreen.kt        # Layar Katalog Utama (Grid View / Compact List)
    ├── theme/                   # Material 3 Theme (Color, Type, Theme)
    └── viewmodel/
        ├── BarangViewModel.kt   # State Holder untuk Koleksi Barang (StateIn Flow)
        └── SettingsViewModel.kt # State Holder untuk Preferensi Settings (DataStore Flow)
```

---

## 3. Penjelasan Layer Arsitektur

### A. Data Layer (Room 3 Database & DataStore)
* **`BarangEntity`** ([BarangEntity.kt](app/src/main/java/com/example/myapplication/data/local/entity/BarangEntity.kt))  
  Entity `@Entity(tableName = "barang")` yang merepresentasikan tabel barang di SQLite dengan kolom `id` (`PrimaryKey`), `namaBarang`, `lokasiSimpan`, `keterangan`, dan `tersedia`.
* **`BarangDao`** ([BarangDao.kt](app/src/main/java/com/example/myapplication/data/local/dao/BarangDao.kt))  
  Interface `@Dao` yang menyediakan query reaktif `observeAll(): Flow<List<BarangEntity>>` serta fungsi CRUD (`insert`, `insertAll`, `update`, `delete`, `count`).
* **`AppDatabase`** ([AppDatabase.kt](app/src/main/java/com/example/myapplication/data/local/database/AppDatabase.kt))  
  Kelas database Room 3 bertipe `@Database` yang menggunakan `AndroidSQLiteDriver()` untuk koneksi database SQLite lokal.
* **`SettingsDataStore`** ([SettingsDataStore.kt](app/src/main/java/com/example/myapplication/data/local/datastore/SettingsDataStore.kt))  
  Pengelolaan preferensi lokal aplikasi menggunakan Jetpack Preferences DataStore untuk menyimpannya opsi `compact_view`.

### B. Repository Layer
* **`BarangRepository`** ([BarangRepository.kt](app/src/main/java/com/example/myapplication/data/repository/BarangRepository.kt))  
  Berfungsi sebagai *Single Source of Truth*. Mengabstraksikan akses data dari `BarangDao`, mengekspos alur data reaktif `barang: Flow<List<BarangEntity>>`, serta menyediakan fungsi `seedIfEmpty()` untuk memasukkan data sampel awal (`initialBarang`).

### C. Application Container
* **`BarangApplication`** ([BarangApplication.kt](app/src/main/java/com/example/myapplication/BarangApplication.kt))  
  Kelas turunan `Application` yang menginisialisasi *instance* `SettingsDataStore`, `AppDatabase`, dan `BarangRepository` secara *lazy* untuk diakses di tingkat aplikasi.

### D. UI & State Management Layer
* **`BarangViewModel`** ([BarangViewModel.kt](app/src/main/java/com/example/myapplication/ui/viewmodel/BarangViewModel.kt))  
  State holder untuk daftar barang yang mengonversi `Flow` dari repository menjadi `StateFlow<List<BarangEntity>>` menggunakan `stateIn()`. Menangani operasi tambah, update, dan hapus barang.
* **`SettingsViewModel`** ([SettingsViewModel.kt](app/src/main/java/com/example/myapplication/ui/viewmodel/SettingsViewModel.kt))  
  State holder untuk pengaturan preferensi yang mengelola alur state `compactView` dari DataStore.
* **`AddViewModel` & `EditViewModel`**  
  State holder khusus layar form input yang mengelola state form menggunakan `MutableStateFlow` (`_uiState`) dan mengeksposnya via `asStateFlow()`.
* **`AddUiState` & `EditUiState`**  
  Data class *immutable* yang menyimpan nilai input form serta kalkulasi `isFormValid`.
* **`AppNavigation`** ([AppNavigation.kt](app/src/main/java/com/example/myapplication/navigation/AppNavigation.kt))  
  Konfigurasi NavHost dengan rute type-safe (`Home`, `Add`, `Detail(id)`, `Edit(id)`) dan injeksi ViewModel.

---

## 4. Cara Menjalankan

### Prasyarat
* **Android Studio**: Android Studio Ladybug atau yang lebih baru.
* **JDK**: Java Development Kit (JDK) 17 atau lebih baru.
* **Min SDK**: Level 24 (Android 7.0 Nougat) atau lebih baru.
* **Target / Compile SDK**: Level 35/36 (Android 15).

### Langkah-Langkah

1. **Buka Project**  
   Buka Android Studio, pilih **Open**, lalu arahkan ke direktori project ini.

2. **Sinkronisasi Gradle**  
   Tunggu hingga proses *Gradle Sync* selesai (atau pilih **File > Sync Project with Gradle Files**).

3. **Jalankan Aplikasi**  
   * Hubungkan perangkat Android fisik (dengan *USB Debugging* aktif) atau jalankan **Android Virtual Device (AVD)**.
   * Pastikan konfigurasi *Run* mengarah pada modul **`app`**.
   * Klik tombol **Run** (▶️) atau tekan `Shift + F10`.

4. **Menjalankan via Command Line (CLI)**  
   ```bash
   # Build APK Debug
   ./gradlew assembleDebug

   # Install APK ke perangkat/emulator yang terhubung
   ./gradlew installDebug
   ```
