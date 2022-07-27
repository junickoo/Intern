import { GeneralService } from './../general.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TracksService } from './../tracks/tracks.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class PlaylistService {
  constructor(
    private tracks: TracksService,
    private http: HttpClient,
    private general: GeneralService
  ) {}
  authToken = 'Bearer ' + this.general.getToken();
  headerDict = {
    Accept: 'application/json',
    'Content-Type': 'application/json',
    Authorization: this.authToken,
  };
  requestOptions = {
    headers: new HttpHeaders(this.headerDict),
  };
  createPlaylist() {
    const url =
      'https://api.spotify.com/v1/users/' +
      window.localStorage.getItem('userId') +
      '/playlists';

    return this.http
      .post(
        url,
        {
          name: 'Testing',
          description: 'testing playlist by rayhan',
          public: false,
        },
        this.requestOptions
      )
      .subscribe((resp) => this.itemsPlaylist(resp));
  }

  itemsUri: any = '';

  itemsPlaylist(playlistId: any) {
    this.itemsUri = this.tracks.tracksUri[0];
    this.tracks.tracksUri[0];
    console.log(playlistId.id);

    for (let i = 1; i < this.tracks.tracksUri.length; i++) {
      // let temp = this.tracks.tracksUri[i].replace(/:/g, '%');
      this.itemsUri = this.itemsUri + ',' + this.tracks.tracksUri[i];
    }
    console.log(this.itemsUri);
    const itemsAdd: any =
      'https://api.spotify.com/v1/playlists/' +
      playlistId.id +
      '/tracks?uris=' +
      this.itemsUri;
    this.http
      .post(itemsAdd, {}, this.requestOptions)
      .subscribe((resp) => console.log(resp));
  }
}
