package com.assignment.resourcemanagement.api.reqs.res;

import com.assignment.resourcemanagement.boundaries.PersistedCaterer;
import org.springframework.hateoas.RepresentationModel;

public class CatererResponse extends RepresentationModel
{
	PersistedCaterer persistedCaterer;
}
