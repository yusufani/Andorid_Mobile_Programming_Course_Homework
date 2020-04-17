1-) Genel Bilgiler
1.1-) Ödev Metni
İçerik :
1. Kullanıcı Giriş Ekranı
a. Şifre Kontrol, Hata Mesajı, Başarılı Giriş Mesajı
b. 3 defa üst üste yanlış kullanıcı adı/şifre girilmesi durumunda uygulama hata mesajı verip
sonlanmalı.
2. Menü Ekranı
a. İlgili Aktivitelere Yönlendirme Ekranı
3. E-mail Oluşturma ve Gönderme Ekranı
a. Kime, Başlık, İçerik ve Ek(Attachment) Bilgileri
b. E-mail Gönderme işlemi başka bir uygulama tarafından gerçekleştirilmeli (Örn: Gmail)
4. Kullanıcıları Listeleme Ekranı
a. RecyclerView yardımı ile sistemde kayıtlı kullanıcıların Kullanıcı Adı, Şifre, Resim
Bilgilerini gösterme
5. Kullanıcı Ayarlarını Gösterme/Değiştirme Ekranı
a. Kullanıcı Adı,Cinsiyet,Boy,Kilo,Yaş,Uygulama Modu(Dark/Light) bilgilerini farklı UI
komponentlerini kullanarak SharedPreferences’a kaydetme ve SharedPreferences’dan
okuma
6. Not Ekleme/Silme/Güncelleme Ekranı
a. Not yazıp dosya olarak ekleme/silme
b. Not üzerinde güncelleme
7. Sensör Ekranı
a. Işık sensörü yardımı ile ortam karanlık iken arka plan siyah yazılar beyaz, ortam
aydınlık iken arka plan beyaz yazılar siyah gösterilmeli
b. Telefon masa üzerinde 5 sn hareketsiz kalırsa (ivmeölçer sensörü kullanılmalı)
uygulama mesaj verip kendini kapatmalı
1.2- Ekstralar
1.	Kullanıcılar yeni kayıt yapabilirler.
2.	Koyu ve Açık mod arasında geçiş yapılabilir.
3.	Telefon veya mesaj geldiğinden toast mesaj ile bilgilendirilir.
1.3- Tüm aktivelerde olan fonksiyonlar
Set_theme (): Aktivetenin rengini kullanıcının sharedPrefencesinden çekip ayarlayan fonksiyon
Set_definations() : Aktivenin ilgili componentlerini tanımlayan fonksiyon
2-) Activityler
2.1- Login

  
(ANA EKRANIN AÇIK MODUNU YAPMADIM GIF ILE RENKLER BIR TÜRLÜ UYMADI :D ) 
•	İlk girişte kayıtlı kullanıcı bulunmadığı için kayıt olmak gerekiyor.
•	Kullanıcılar username ve şifreyi girdikten sonra giriş yapabilir veya kayıt olabilirler. 
•	Kullanıcı bilgileri User Sınıfı olarak Private modda dosyaya yazılmaktadır.
•	Usernamede “\n” geçerse onu “ “ ile değiştirmektedir.
•	3 kere hatalı giriş sonrası Toast mesajı verip uygulama kapanmaktadır.
•	Username Unique olmalıdır. ( isExists Fonksiyonu )
•	Username boşluk içeremez. 
•	Checklogin fonksiyonu giriş bilgilerini control eder .

2.2- Menu
  
•	İlgili aktivelere yönlendiren menu sistemi







2.3-  Mail
  
•	Ekle butonu ile ek eklenebilir. Bunun için Intent.ACTION_GET_CONTENT kullanılır.
•	Gönder butonu ile mail uygulaması seçilip mail ekranına yönlendirilebilir.







2.4-  People
  
•	Kayıtlı kullanıcılar listelenir.
•	Şifre alanına dokununca şifre görünür hale gelir.
•	Adapter_People sınıfı kullanıcıları RecyclerViewe bağlar.












2.5- Settings
  
•	Boş bırakılan değerler 0 olarak alınır.
•	Tüm bilgiler kullanıcıya özel olarak sharedPrefenceste saklanır .






2.6- Note_Manager
  
    
•	Notlar gösterilebilir ve silinebilir.
•	Kayıt başlığı girilerek yeni kayıt oluşturulabilir.
•	Kayıtlara otomatik olarak tarih eklenir.
•	Adapter_Note sınıfı notları RecyclerView’a bağlar.
•	Kayıt başlığı unique olmalıdır.






2.7- Sensor

  
•	Cihazda mevcut sensorler yazdırılmıştır.
•	Işık için eşik değeri bar sayesinde dinamil olarak ayarlanabilir.
•	İvmeölçerden gelen değerler 100 ile çarpılıp integer olarak kaydedilmiştir. Ayrıca yatay konumda iken 50 birimlik epsilon değeri belirlendi.
•	Eğer değerler 0,0,9.81 değerlerine +/- epsilon değeri kadar yakınsa timer başlatılır  . 5 sn sonra uygulama kapanır.
