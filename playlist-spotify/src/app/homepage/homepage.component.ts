import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css'],
})
export class HomepageComponent implements OnInit {
  constructor() {
    alert(
      'Masih dalam masa percobaan' +
        '\n \nlaporkan bug dan error ke rayhanjr2106@gmail.com'
    ),
      alert(
        'Tata cara penggunaan : \n 1.) Kirim email spotifymu ke rayhanjr2106@gmail.com \n 2.) Jika sudah terkonfirmasi silahkan login menggunakan spotify \n 3.) Tekan generate playlist \n 4.) Playlist akan muncul dengan nama Testing'
      ),
      alert(
        'Data yg di ambil untuk pembuatan playlist ini adalah data penggunaan spotify di 1 bulan terakhir'
      );
  }
  ngOnInit(): void {}
  CLIENT_ID = 'fde979e55ffe45dcb9d7550674280783';
  REDIRECT_URI = 'https://junickoo.github.io/spotify-playlist-create/';
  scopes =
    'user-top-read user-follow-read playlist-modify-public playlist-modify-private';
  AUTH_ENDPOINT = 'https://accounts.spotify.com/authorize';
  RESPONSE_TYPE = 'token';
  notification() {
    alert('Masih dalam masa percobaan');
    alert(
      'Tata cara penggunaan : \n 1.) Kirim email spotifymu ke rayhanjr2106@gmail.com \n 2.) Jika sudah terkonfirmasi silahkan login menggunakan spotify \n 3.) Tekan generate playlist \n 4.) Playlist akan muncul dengan nama Testing'
    );
  }
}
