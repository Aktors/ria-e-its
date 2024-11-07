import {FormControl} from '@angular/forms';

export interface SearchFormGroup {
  version: FormControl<string | null>;
  filter: FormControl<string | null>;
}

export interface SearchCriteria {
  version: string | null;
  filter: string | null;
}
