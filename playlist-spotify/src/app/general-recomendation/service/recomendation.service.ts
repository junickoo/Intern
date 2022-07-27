import { TracksService } from './../../service/tracks/tracks.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class RecomendationService {
  constructor(private tracks: TracksService) {}
  getArtisttop() {
    this.tracks.topTracks().subscribe((resp) => this.getArtistTopid(resp));
  }

  artistTopid: any;
  listArtists: any[] = [];
  getArtistTopid(items: any) {
    this.listArtists = [];
    var rand = Math.floor(Math.random() * 10);
    this.artistTopid = items.items[rand].artists[0].id;
    this.listArtists.push(this.artistTopid);
    this.tracks.artistId = this.listArtists;
    console.log(items.items[rand].artists[0].name);
  }
  dataset: any;
  setDataset() {
    this.dataset = this.tracks.relatedartistId;
  }
  compareTopartist() {
    this.listArtists = [];
    console.log(this.dataset);
    console.log(this.tracks.artistId);
    var artistIdset;
    for (let i = 0; i < this.tracks.artistId.length; i++) {
      for (let j = 0; j < this.dataset.length; j++) {
        if (this.dataset[j] === this.tracks.artistId[i]) {
          if (this.dataset[j] != this.artistTopid) {
            this.listArtists.push(this.dataset[j]);
          }
        }
      }
    }
    console.log(this.listArtists);
  }

  dataSetFinal() {
    for (let i = 0; i < this.tracks.relatedartistId.length; i++) {
      let count = 0;
      for (let j = 0; j < this.dataset.length; j++) {
        if (this.tracks.relatedartistId[i] === this.dataset[j]) {
          count++;
        }
      }
      if (count === 0) {
        this.dataset.push(this.tracks.relatedartistId[i]);
      }
    }
    console.log(this.dataset);
  }
}
