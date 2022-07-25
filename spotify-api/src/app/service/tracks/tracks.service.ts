import { GeneralService } from './../general.service';
import { ComponentFactoryResolver, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TracksService {
  constructor(private generalService: GeneralService) {}
  artistId: any[] = [];
  topList() {
    return this.generalService
      .getQuery('me/top/artists?time_range=short_term&limit=10&offset=0')
      .subscribe((resp) => this.getArtistid(resp));
  }

  getArtistid(items: any) {
    this.artistId = [];
    const resp = items;
    for (let i = 0; i < resp.items.length; i++) {
      this.artistId.push(resp.items[i].id);
    }
  }

  randTemp: any[] = [];
  tracksUri: any[] = [];

  //randomize list of tracks (artist top, album, etc)
  tracksRandomizer(tracksList: any) {
    console.log('\nArtists : ' + tracksList.tracks[0].artists[0].name);
    const arrayTemp = [];
    for (let i = 0; i < 3; i++) {
      let rand = Math.floor(Math.random() * (tracksList.tracks.length - 1));
      if (arrayTemp.length > 0) {
        for (let j = 0; j < arrayTemp.length; j++) {
          if (rand === arrayTemp[j]) {
            rand = Math.floor(Math.random() * (tracksList.tracks.length - 1));
            j = 0;
          }
        }
      }
      arrayTemp.push(rand);
      this.tracksUri.push(tracksList.tracks[rand].uri);
      console.log(tracksList.tracks[rand].uri);
      console.log(this.tracksUri);
    }
  }

  //artist top tracks
  randomTracks() {
    console.log(this.artistId.length);
    this.tracksUri = [];
    for (let i = 0; i < this.artistId.length; i++) {
      const url = 'artists/' + this.artistId[i] + '/top-tracks?market=ID';
      this.generalService
        .getQuery(url)
        .subscribe((resp) => this.tracksRandomizer(resp));
    }
  }
}
