import { PlaylistService } from './../service/playlist/playlist.service';
import { TracksService } from './../service/tracks/tracks.service';
import { RecomendationService } from './../general-recomendation/service/recomendation.service';
import { GeneralService } from './../service/general.service';
import { Component, OnInit } from '@angular/core';
import { timer } from 'rxjs';

@Component({
  selector: 'playlist',
  templateUrl: './playlist.component.html',
  styleUrls: ['./playlist.component.css'],
})
export class PlaylistComponent implements OnInit {
  constructor(
    private general: GeneralService,
    private recomendation: RecomendationService,
    private tracks: TracksService,
    private playlist: PlaylistService
  ) {
    general.getUserPorfile();
  }

  ngOnInit(): void {}

  getToken() {
    this.general.getToken();
  }

  comparing() {
    this.recomendation.setDataset();
    this.recomendation.compareTopartist();
  }
  getRelated() {
    this.tracks.artistId = this.recomendation.listArtists;
    this.tracks.getRelated();
  }
  trim() {
    this.recomendation.dataSetFinal();
    this.tracks.randomTracks(this.recomendation.dataset);
  }
  playlistGenerate() {
    this.recomendation.getArtisttop();
    window.setTimeout(() => {
      this.tracks.getRelated();
    }, 500);
    window.setTimeout(() => {
      this.tracks.topList();
    }, 500);
    window.setTimeout(() => {
      this.comparing();

      this.getRelated();
    }, 1000);
    window.setTimeout(() => {
      this.trim();
    }, 1100);
    window.setTimeout(() => {
      this.playlist.createPlaylist();
    }, 1200);
  }
  // playlistCreate() {
  //   this.playlist.createPlaylist();
  // }
}
