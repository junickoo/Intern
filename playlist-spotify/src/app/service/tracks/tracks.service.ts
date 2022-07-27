import { GeneralService } from './../general.service';
import { ComponentFactoryResolver, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TracksService {
  constructor(private generalService: GeneralService) {}
  artistId: any[] = [];
  relatedartistId: any[] = [];
  topList() {
    return this.generalService
      .getQuery('me/top/artists?time_range=short_term&limit=10&offset=0')
      .subscribe((resp) => this.getArtistid(resp));
  }

  topTracks() {
    return this.generalService.getQuery(
      'me/top/tracks?time_range=short_term&limit=10&offset=0'
    );
  }

  tracksArtistId(items: any) {
    var topartistId = items.items[0].artists[0].name;
    return topartistId;
  }

  getArtistid(items: any) {
    this.artistId = [];
    const resp = items;
    for (let i = 0; i < resp.items.length; i++) {
      this.artistId.push(resp.items[i].id);
      console.log(resp.items[i].name);
    }
  }

  getRelated() {
    let j = 0;
    if (this.artistId.length < 6) {
      j = this.artistId.length;
    } else if (this.artistId.length > 6) {
      j = 5;
    }
    for (let i = 0; i < j; i++) {
      this.relatedartistId = [];
      const url = 'artists/' + this.artistId[i] + '/related-artists';
      this.generalService
        .getQuery(url)
        .subscribe((resp) => this.getRelatedartistId(resp, this.artistId[i]));
    }
  }

  getRelatedartistId(items: any, artistId: any) {
    for (let i = 0; i < items.artists.length; i++) {
      this.relatedartistId.push(items.artists[i].id);
    }
    console.log(artistId);
    this.relatedartistId.push(artistId);
    console.log(this.relatedartistId);
  }

  randTemp: any[] = [];
  tracksUri: any[] = [];

  //randomize list of tracks (artist top, album, etc)
  tracksRandomizer(tracksList: any) {
    console.log('\nArtists : ' + tracksList.tracks[0].artists[0].name);
    const arrayTemp = [];
    for (let i = 0; i < 1; i++) {
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
    }
  }

  //artist top tracks
  randomTracks(artistsId: any) {
    console.log(artistsId.length);
    this.tracksUri = [];
    for (let i = 0; i < artistsId.length; i++) {
      const url = 'artists/' + artistsId[i] + '/top-tracks?market=ID';
      this.generalService
        .getQuery(url)
        .subscribe((resp) => this.tracksRandomizer(resp));
    }
    console.log(this.tracksUri);
  }
}
