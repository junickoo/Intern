import { RecomendationService } from './../service/recomendation.service';
import { TracksService } from './../../service/tracks/tracks.service';
import { GeneralService } from './../../service/general.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-general-recomendation',
  templateUrl: './general-recomendation.component.html',
  styleUrls: ['./general-recomendation.component.css'],
})
export class GeneralRecomendationComponent implements OnInit {
  constructor(
    private service: GeneralService,
    private tracks: TracksService,
    private recomendation: RecomendationService
  ) {}

  getTopTracks() {
    this.recomendation.getArtisttop();
  }

  getRelated() {
    this.tracks.artistId = this.recomendation.listArtists;
    this.tracks.getRelated();
  }
  comparing() {
    this.recomendation.setDataset();
    this.recomendation.compareTopartist();
  }

  trim() {
    this.recomendation.dataSetFinal();
    this.tracks.randomTracks(this.recomendation.dataset);
  }
  ngOnInit(): void {}
}
