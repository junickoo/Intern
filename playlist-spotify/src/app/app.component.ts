import { PlaylistService } from './service/playlist/playlist.service';
import { TracksService } from './service/tracks/tracks.service';
import { GeneralService } from './service/general.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  constructor(
    private service: GeneralService,
    private tracks: TracksService,
    private playlist: PlaylistService
  ) {}
  artistList() {
    this.tracks.randomTracks(this.tracks.artistId);
    // this.service
    //   .getQuery('me/top/artists?time_range=medium_term&limit=10&offset=5')
    //   .subscribe((resp) => console.log(resp));
  }
  getRelatedTracks() {
    this.tracks.randomTracks(this.tracks.relatedartistId);
  }
  getRelated() {
    this.tracks.getRelated();
  }
  toplist() {
    this.tracks.topList();
  }
  getUser() {
    this.service.getUserPorfile();
  }
  playlistCreate() {
    this.playlist.createPlaylist();
  }
  CLIENT_ID = 'fde979e55ffe45dcb9d7550674280783';
  REDIRECT_URI = 'http://localhost:4200';
  scopes =
    'user-top-read user-follow-read playlist-modify-public playlist-modify-private';
  AUTH_ENDPOINT = 'https://accounts.spotify.com/authorize';
  RESPONSE_TYPE = 'token';
}
