import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class GeneralService {
  constructor(private http: HttpClient) {}

  getQuery(query: any) {
    let url = '	https://api.spotify.com/v1/';
    let curl = url + query;
    const token = /*localStorage.getItem('token')*/ this.getToken();
    const authToken = 'Bearer ' + token;
    const headerDict = {
      Accept: 'application/json',
      'Content-Type': 'application/json',
      Authorization: authToken,
    };
    const requestOptions = {
      headers: new HttpHeaders(headerDict),
    };

    return this.http.get(curl, requestOptions);
  }

  getToken() {
    let token = window.localStorage.getItem('token');
    const hash = window.location.hash;
    const accessToken: string = hash
      .substring(1)
      .split('&')
      .find((elem) => elem.startsWith('access_token'))
      ?.split('=')[1]!;
    window.localStorage.setItem('token', accessToken);
    return accessToken;
  }

  getUserPorfile() {
    this.getQuery('me').subscribe((resp) => this.getUserid(resp));
  }
  userId: any | undefined;

  getUserid(resp: any) {
    this.userId = resp.id;
    window.localStorage.setItem('userId', this.userId);
    console.log(this.userId);
  }
}
