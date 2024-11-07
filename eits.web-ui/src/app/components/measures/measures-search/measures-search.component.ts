import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatInput} from '@angular/material/input';
import {MatOption, MatSelect} from '@angular/material/select';
import {NgForOf} from '@angular/common';
import {MatChip, MatChipInput} from '@angular/material/chips';
import {MatIcon} from '@angular/material/icon';
import {MatAutocomplete, MatAutocompleteTrigger} from '@angular/material/autocomplete';

import {ClassifierStore} from '../../../services/classifier.store';
import {SearchFormGroup} from './measures-search.model';
import {MatGridList, MatGridTile} from '@angular/material/grid-list';
import {MatIconButton} from '@angular/material/button';
import {QueryCriterionDto} from '../../../model/table.type';


@Component({
  selector: 'app-measures-search',
  standalone: true,
  imports: [
    MatLabel,
    MatFormField,
    ReactiveFormsModule,
    MatInput,
    MatSelect,
    MatOption,
    NgForOf,
    MatChip,
    MatIcon,
    MatChipInput,
    MatAutocompleteTrigger,
    FormsModule,
    MatAutocomplete,
    MatGridList,
    MatGridTile,
    MatIconButton
  ],
  templateUrl: './measures-search.component.html',
  styleUrl: './measures-search.component.css'
})
export class MeasuresSearchComponent implements OnInit {
  form: FormGroup<SearchFormGroup>;
  versions: string[] = [];

  @Output() search = new EventEmitter<QueryCriterionDto[]>();
  @Output() versionChange = new EventEmitter<string>();

  constructor(
    private classifierStore: ClassifierStore,
    private fb: FormBuilder) {
    this.form = this.fb.group<SearchFormGroup>({
      version: new FormControl(''),
      filter: new FormControl('')
    });
  }

  ngOnInit(): void {
    this.classifierStore.versions$
      .subscribe(versions => {
        this.versions = versions;
        if (versions.length > 0)
          this.form.controls.version.setValue(versions[0]);
      });
    this.form.controls.version.valueChanges.subscribe((value) => {
      if(value) this.versionChange.emit(value);
    })
  }

  performSearch(): void {
    if (this.form.valid) {
      let filters:QueryCriterionDto[] = [];
      if(this.form.controls.filter.value){
        filters.push({ field: 'title', value: this.form.controls.filter.value})
      }
      this.search.emit(filters);
    }
  }
}
