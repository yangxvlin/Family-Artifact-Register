package com.unimelb.family_artifact_register.UI.Artifact.ArtifactMap.Util.MarkerStrategy;

public class MarkerZoomStrategyFactory {
    private static MarkerZoomStrategyFactory markerZoomStrategyFactory = new MarkerZoomStrategyFactory();
    public static MarkerZoomStrategyFactory getMarkerZoomStrategyFactory() {
        return markerZoomStrategyFactory;
    }

    public MarkerZoomStrategy getMarkerZoomStrategy(int markerLength) {
        if (markerLength == 1) {
            return new SingleMarkerZoomStrategy();
        } else if (markerLength > 1) {
            return new MultipleMarkerZoomStrategy();
        } else {
            return new ZeroMarkerZoomStrategy();
        }
    }
}
