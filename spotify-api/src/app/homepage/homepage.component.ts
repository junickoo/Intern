import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css'],
})
export class HomepageComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
  CLIENT_ID = 'fde979e55ffe45dcb9d7550674280783';
  REDIRECT_URI = 'http://localhost:4200/playlist/';
  scopes =
    'user-top-read user-follow-read playlist-modify-public playlist-modify-private';
  AUTH_ENDPOINT = 'https://accounts.spotify.com/authorize';
  RESPONSE_TYPE = 'token';
}
